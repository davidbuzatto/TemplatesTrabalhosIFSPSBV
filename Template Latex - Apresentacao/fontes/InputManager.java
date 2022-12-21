/**
 * O InputManager gerencia a entrada de teclas e eventos do mouse.
 * Os eventos são mapeados para GameActions.
 *
 * @author David Buzatto
 */
public class InputManager implements KeyListener, MouseListener,
    MouseMotionListener, MouseWheelListener {
    
    /**
     * Cria um novo InputManager que ouve as entradas de um componente 
     * específico.
     */
    public InputManager( Component comp ) {
        this.comp = comp;
        mouseLocation = new Point();
        centerLocation = new Point();

        // registra os ouvintes de tecla e do mouse
        comp.addKeyListener( this );
        comp.addMouseListener( this );
        comp.addMouseMotionListener( this );
        comp.addMouseWheelListener( this );
        
        /*
         * permite a entrada da tecla TAB e outras teclas normalmente 
         * usadas pelo focus traversal.
         */
        comp.setFocusTraversalKeysEnabled( false );
    }
    
}