/**
 * A classe ScreenManager gerencia a inicialização e visualização de
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
    	// obtém o ambiente gráfico
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        // obtém o dispositivo gráfico padrão
        device = environment.getDefaultScreenDevice();
    }
    
    /**
     * Entra no modo de tela cheia o muda o modo de visualização.
     * Se o modo de visualização especificado é null ou não compatível 
     * com este dispositivo, ou o modo de visualização não puder ser 
     * alterado nesse sistema, o modo de visualização atual é 
     * utilizado.
     * <p>
     * A visualização usa um BufferStrategy com 2 buffers.
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