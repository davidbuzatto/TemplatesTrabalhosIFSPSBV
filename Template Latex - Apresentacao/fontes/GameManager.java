public class GameManager extends GameCore {

    /**
     * Obtém o tile que a Sprite colide. Somente X ou Y da Sprite 
     * deve ser mudado não ambos. Retorna null se nenhuma colisão 
     * for detectada.
     */
    public Point getTileCollision( Sprite sprite,
            float newX, float newY ) {
        
        float fromX = Math.min( sprite.getX(), newX );
        float fromY = Math.min( sprite.getY(), newY );
        float toX = Math.max( sprite.getX(), newX );
        float toY = Math.max( sprite.getY(), newY );
        
        // obtem a localização do tile
        int fromTileX = TileMapRenderer.pixelsToTiles( fromX );
        int fromTileY = TileMapRenderer.pixelsToTiles( fromY );
        int toTileX = TileMapRenderer.pixelsToTiles(
                toX + sprite.getWidth() - 1 );
        int toTileY = TileMapRenderer.pixelsToTiles(
                toY + sprite.getHeight() - 1);
        
        // checa cada tile para verificar a colisão
        for ( int x = fromTileX; x <= toTileX; x++ ) {
            for ( int y = fromTileY; y <= toTileY; y++ ) {
                if ( x < 0 || x >= map.getWidth() ||
                        map.getTile( x, y ) != null ) {
                    // colisão achada, retorna o tile
                    pointCache.setLocation( x, y );
                    return pointCache;
                }
            }
        }
        // nenhuma colisão achada
        return null;
    }
    
    /**
     * Verifica se duas sprites colidiram entre si. Retorna false 
     * caso duas Sprites sejam a mesma. Retorna false se um uma 
     * das Sprites não estiver viva.
     */
    public boolean isCollision( Sprite s1, Sprite s2 ) {
        
        // se as sprites são a mesma, retorn false
        if ( s1 == s2 )
            return false;
        
        // se uma das sprites é uma criatura morta, retorna false
        if ( s1 instanceof Creature && ! ( ( Creature ) s1 ).isAlive() )
            return false;
        if ( s2 instanceof Creature && !( ( Creature ) s2 ).isAlive() )
            return false;
        
        // obtem a localização em pixel das sprites
        int s1x = Math.round( s1.getX() );
        int s1y = Math.round( s1.getY() );
        int s2x = Math.round( s2.getX() );
        int s2y = Math.round( s2.getY() );
        
        // verifica se as bordas das sprites se interceptam
        return ( s1x < s2x + s2.getWidth() &&
                s2x < s1x + s1.getWidth() &&
                s1y < s2y + s2.getHeight() &&
                s2y < s1y + s1.getHeight() );
    }
    
    /**
     * Obtém a Sprite que colide com uma Sprite específica,
     * ou null se nenhum Sprite colide com a Sprite especificada.
     */
    public Sprite getSpriteCollision(Sprite sprite) {
        
        // itera pela lista de sprites
        Iterator i = map.getSprites();
        
        while ( i.hasNext() ) {
            Sprite otherSprite = ( Sprite ) i.next();
            if ( isCollision( sprite, otherSprite ) )
                // colisão encontrada, retorna a sprite
                return otherSprite;
        }
        // sem colisão
        return null;
    }
    
    /**
     * Atualiza as criaturas, usando gravidade para as criaturas 
     * que não estão voando e verifica colisão.
     */
    private void updateCreature( Creature creature, long elapsedTime ) {
        
        // usa gravidade
        if ( !creature.isFlying() )
            creature.setVelocityY( creature.getVelocityY() +
                    GRAVITY * elapsedTime );
        
        // altera x
        float dx = creature.getVelocityX();
        float oldX = creature.getX();
        float newX = oldX + dx * elapsedTime;
        Point tile = getTileCollision( creature, newX, creature.getY() );
        
        if ( tile == null ) {
            creature.setX( newX );
        } else {    
            // alinha com a borda do tile
            if ( dx > 0 ) {
                creature.setX(
                        TileMapRenderer.tilesToPixels( tile.x ) -
                        creature.getWidth() );
            } else if ( dx < 0 ) {
                creature.setX(
                        TileMapRenderer.tilesToPixels( tile.x + 1 ) );
            }
            creature.collideHorizontal();
        }
        
        if ( creature instanceof Player )
            checkPlayerCollision( ( Player ) creature, false );
        
        // troca y
        float dy = creature.getVelocityY();
        float oldY = creature.getY();
        float newY = oldY + dy * elapsedTime;
        tile = getTileCollision( creature, creature.getX(), newY );
        
        if ( tile == null ) {
            creature.setY( newY );
        } else {
            // alinha com a borda do tile
            if ( dy > 0 ) {
                creature.setY(
                        TileMapRenderer.tilesToPixels( tile.y ) -
                        creature.getHeight() );
            } else if ( dy < 0 ) {
                creature.setY(
                        TileMapRenderer.tilesToPixels( tile.y + 1 ) );
            }
            creature.collideVertical();
        }
        if ( creature instanceof Player ) {
            boolean canKill = ( oldY < creature.getY() );
            checkPlayerCollision( ( Player ) creature, canKill );
        }
        
        // se o jogador cai (y muito alto), tira vida e reinicia
        if ( creature instanceof Player ) {
            // se o jogador está além do pixel 2000 de altura, morre
            if ( creature.getY() > 2000 ) {
                creature.setState( creature.STATE_DEAD );
                quantidadeVidas--;
            }
            
        }
        
    }
    
    /**
     * Verifica colisão entre o jogador e outras sprites. Se canKill 
     * é true a colisão com as criaturas irá matá-las.
     */
    public void checkPlayerCollision( Player player,
            boolean canKill ) {
        
        if ( !player.isAlive() )
            return;
        
        // verifica a colisão do jogador com outras Sprites
        Sprite collisionSprite = getSpriteCollision( player );
        
        if ( collisionSprite instanceof PowerUp ) {
            acquirePowerUp( ( PowerUp ) collisionSprite );
        } else if ( collisionSprite instanceof Creature ) {
            
            Creature badguy = ( Creature ) collisionSprite;
            
            if ( canKill ) {
                // mata o inimigo a faz o jogador oscilar
                soundManager.play( boopSound );
                badguy.setState( Creature.STATE_DYING );
                quantidadePontos += 100;
                player.setY( badguy.getY() - player.getHeight() );
                player.jump( true );
            } else {
                // jogador morre
                player.setState( Creature.STATE_DYING );
                // decrementa quantidade de vidas
                quantidadeVidas--;
            }
        }
    }
}