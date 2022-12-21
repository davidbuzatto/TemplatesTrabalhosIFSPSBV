/**
 * A classe ScreenManager gerencia a inicializa��o e visualiza��o de
 * modos de tela cheia.
 *
 * @author David Buzatto
 */
public class ScreenManager {
    
    private GraphicsDevice device;
    
    /**
     * Cria um novo ScreenManager.
     */
    public ScreenManager() {
    	// obt�m o ambiente gr�fico
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        // obt�m o dispositivo gr�fico padr�o
        device = environment.getDefaultScreenDevice();
    }
    
    /**
     * Entra no modo de tela cheia o muda o modo de visualiza��o.
     * Se o modo de visualiza��o especificado � null ou n�o compat�vel 
     * com este dispositivo, ou o modo de visualiza��o n�o puder ser 
     * alterado nesse sistema, o modo de visualiza��o atual � 
     * utilizado.
     * <p>
     * A visualiza��o usa um BufferStrategy com 2 buffers.
     */
    public void setFullScreen( DisplayMode displayMode ) {
    	
        final JFrame frame = new JFrame();
        
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setUndecorated( true );
        frame.setIgnoreRepaint( true );
        frame.setResizable( false );
        device.setFullScreenWindow( frame );
        
        if ( displayMode != null && 
                device.isDisplayChangeSupported() ) {
            try {
                device.setDisplayMode( displayMode );
            } catch (IllegalArgumentException ex) { }
            // fix para o Mac OS
            frame.setSize( displayMode.getWidth(), 
                    displayMode.getHeight() );
        }
        
        // evita deadlock no Java 1.4
        try {
            EventQueue.invokeAndWait(
                    new Runnable() {
                        public void run() {
                            frame.createBufferStrategy( 2 );
                        }
                    }
            );
        } catch ( InterruptedException ex ) {
            // ignora
        } catch ( InvocationTargetException  ex ) {
            // ignora
        }
    }
    
}