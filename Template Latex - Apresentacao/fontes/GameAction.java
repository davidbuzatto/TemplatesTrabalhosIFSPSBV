/**
 * A classe GameAction é uma abstração para uma ação iniciada pelo 
 * usuário, como pular ou mover. As GameActions podem ser mapeadas 
 * para teclas ou mouse usando o InputManager.
 *
 * @author David Buzatto
 */
public class GameAction {
    
    /**
     * Comportamento normal. O método isPressed() retorna true quando a
     * tecla é mantida pressionada.
     */
    public static final int NORMAL = 0;
    
    /**
     * Comportamento de pressionamento inicial. O método isPressed() 
     * retorna true somente depois que a tecla é pressionada pela 
     * primeira vez, e não novamente ate que a tecla seja solta e 
     * pressionada novamente.
     */
    public static final int DETECT_INITAL_PRESS_ONLY = 1;
    
    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    
    private String name;
    private int behavior;
    private int amount;
    private int state;
    
    /**
     * Cria uma nova GameAction com o comportamento especificado.
     */
    public GameAction( String name, int behavior ) {
        this.name = name;
        this.behavior = behavior;
        reset();
    }
    
    /**
     * Reseta esta GameAction, fazendo parecer que esta não foi 
     * pressionada.
     */
    public void reset() {
        state = STATE_RELEASED;
        amount = 0;
    }
    
    /**
     * Sinaliza que a tecla foi pressionada.
     */
    public synchronized void press() {
        press( 1 );
    }
    
    /**
     * Sinaliza que a tecla foi solta.
     */
    public synchronized void release() {
        state = STATE_RELEASED;
    }
    
    /**
     * Retorna se a tecla foi pressionada ou não desde a última checagem.
     */
    public synchronized boolean isPressed() {
        return ( getAmount() != 0 );
    }
    
}