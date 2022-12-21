/**
 * Classe abstrata utilizada como base do jogo. As subclasses devem 
 * implementar o m�todo draw().
 *
 * @author David Buzatto
 */
public abstract class GameCore {
    
    protected static final int FONT_SIZE = 24;
    
    // modos de visualiza��o
    private static final DisplayMode POSSIBLE_MODES[] = {
        new DisplayMode( 800, 600, 16, 0 ),
        new DisplayMode( 800, 600, 32, 0 ),
        new DisplayMode( 800, 600, 24, 0 ),
        new DisplayMode( 640, 480, 16, 0 ),
        new DisplayMode( 640, 480, 32, 0 ),
        new DisplayMode( 640, 480, 24, 0 ),
        new DisplayMode( 1024, 768, 16, 0 ),
        new DisplayMode( 1024, 768, 32, 0 ),
        new DisplayMode( 1024, 768, 24, 0 ),
    };
    
    // indica se o jogo est� sendo executado
    private boolean isRunning;
    
    // tela do jogo
    protected ScreenManager screen;
    
    /**
     * Sinaliza ao loop do jogo que � hora de terminar.
     */
    public void stop() {
        isRunning = false;
    }
    
    
    /**
     * Chama init() e gameLoop().
     */
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screen.restoreScreen();
            lazilyExit();
        }
    }
    
    
    /**
     * Finaliza a m�quina virtual usando uma thread daemon.
     * A thread daemon aguarda 2 segundos ent�o chama System.exit(0).
     * Como a m�quina virtual deve finalizar apenas quando o daemon 
     * estiver rodando, isso d� certeza que System.exit(0) � chamado 
     * somente quanto necess�rio. Isso se faz necess�rio para quando 
     * o sistema de som do Java estiver sendo executado.
     */
    public void lazilyExit() {
        Thread thread = new Thread() {
            public void run() {
                // primeiro aguarda que a m�quina virtual finaliza 
                // por si pr�pria
                try {
                    Thread.sleep( 2000 );
                } catch ( InterruptedException ex ) { }
                // o sistema ainda est� rodando, ent�o for�a a 
                // finaliza��o
                System.exit( 0 );
            }
        };
        thread.setDaemon( true );
        thread.start();
    }
    
    
    /**
     * Configura o modo de tela cheia, inicializa e cria os objetos.
     */
    public void init() {
        screen = new ScreenManager();
        DisplayMode displayMode =
                screen.findFirstCompatibleMode( POSSIBLE_MODES );
        screen.setFullScreen(displayMode);
        
        Window window = screen.getFullScreenWindow();
        window.setFont( new Font( "Dialog", Font.PLAIN, FONT_SIZE ) );
        window.setBackground( Color.BLUE );
        window.setForeground( Color.WHITE );
        
        isRunning = true;
    }
    
    
    /**
     * Carrega uma imagem.
     */
    public Image loadImage( String name ) {
        return new ImageIcon( getClass().getResource( 
                "/recursos/imagens/" + name ) ).getImage();
    }
    
    
    /**
     * Executa o game loop at� que stop() seja chamado.
     */
    public void gameLoop() {
    	
    	// obt�m a hora atual do sistema
        long startTime = System.currentTimeMillis();
        long currTime = startTime;
        
        // enquanto est� executando...
        while ( isRunning ) {
        	
        	// calcula o tempo que passou
            long elapsedTime =
                    System.currentTimeMillis() - currTime;
            currTime += elapsedTime;
            
            // atualiza
            update( elapsedTime );
            
            // desenha
            Graphics2D g = screen.getGraphics();
            draw( g );
            g.dispose();
            screen.update();
            
            // n�o forme, executando da forma mais r�pida poss�vel
            /*try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) { }*/
        }
    }
    
    /**
     * Atualiza o estado do jogo/anima��o baseado da quantidade 
     * de tempo que passou entre a itera��o atual e a anterior.
     */
    public void update( long elapsedTime ) {
        // n�o faz nada
    }
    
    /**
     * Desenha na tela. As subclasses devem sobrescrever esse m�todo.
     */
    public abstract void draw( Graphics2D g );
    
}