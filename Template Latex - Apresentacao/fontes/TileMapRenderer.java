public class TileMapRenderer {
    
    /**
     * Desenha o TileMap especificado.
     */
    public void draw( Graphics2D g, TileMap map,
            int screenWidth, int screenHeight ) {
        
        Sprite player = map.getPlayer();
        int mapWidth = tilesToPixels( map.getWidth() );
        
        // obtém a posição de scrolling do mapa, baseado 
        // na posição do jogador
        int offsetX = screenWidth / 2 -
                Math.round( player.getX() ) - TILE_SIZE;
        offsetX = Math.min( offsetX, 0 );
        offsetX = Math.max( offsetX, screenWidth - mapWidth );
        
        // obtém o offset de y para desenhar todas as sprites 
        // e tiles
        int offsetY = screenHeight -
                tilesToPixels( map.getHeight() );
        
        // desenha um fundo preto se necessário
        if ( background == null ||
                screenHeight > background.getHeight( null ) ) {
            g.setColor( Color.BLACK );
            g.fillRect( 0, 0, screenWidth, screenHeight );
        }
        
        // desenha a imagem de fundo usando parallax
        if ( background != null ) {
            int x = offsetX *
                    ( screenWidth - background.getWidth( null ) ) /
                    ( screenWidth - mapWidth );
            int y = screenHeight - background.getHeight( null );
            
            g.drawImage( background, x, y, null );
        }
        
        // desenha os tiles visíveis
        int firstTileX = pixelsToTiles( -offsetX );
        int lastTileX = firstTileX +
                pixelsToTiles( screenWidth ) + 1;
        for ( int y = 0; y < map.getHeight(); y++ ) {
            for ( int x = firstTileX; x <= lastTileX; x++ ) {
                Image image = map.getTile( x, y );
                if ( image != null ) {
                    g.drawImage( image,
                            tilesToPixels( x ) + offsetX,
                            tilesToPixels( y ) + offsetY,
                            null );
                }
            }
        }
        
        // desenha o jogador
        g.drawImage( player.getImage(),
                Math.round( player.getX() ) + offsetX,
                Math.round( player.getY() ) + offsetY,
                null );
        
        // desenha as sprites
        Iterator i = map.getSprites();
        while ( i.hasNext() ) {
            Sprite sprite = ( Sprite ) i.next();
            int x = Math.round( sprite.getX() ) + offsetX;
            int y = Math.round( sprite.getY() ) + offsetY;
            g.drawImage( sprite.getImage(), x, y, null );
            
            // acorda a critura quando a mesma estiver na tela
            if ( sprite instanceof Creature &&
                    x >= 0 && x < screenWidth ) {
                ( ( Creature ) sprite ).wakeUp();
            }
        }
    }
}