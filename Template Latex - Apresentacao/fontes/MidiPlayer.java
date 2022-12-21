/*
 * A classe MidiPlayer � respons�vel por reproduzir arquivos midi.
 *
 * @author David Buzatto
 */
public class MidiPlayer implements MetaEventListener {
    
    // Evento meta Midi
    public static final int END_OF_TRACK_MESSAGE = 47;
    
    private Sequencer sequencer;
    private boolean loop;
    private boolean paused;
    
    /**
     * Cria um novo MidiPlayer.
     */
    public MidiPlayer() {
        try {
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addMetaEventListener( this );
        } catch ( MidiUnavailableException ex ) {
            sequencer = null;
        }
    }
    
    /**
     * Executa uma seq��ncia, realizando um loop opcionalmente. 
     * Esse m�todo retorna imediatamente.
     * A seq��ncia n�o � executada se n�o for v�lida.
     */
    public void play( Sequence sequence, boolean loop ) {
        if ( sequencer != null && sequence != null ) {
            try {
                sequencer.setSequence( sequence );
                sequencer.start();
                this.loop = loop;
                
            } catch ( InvalidMidiDataException ex ) {
                ex.printStackTrace();
            }
        }
    }
}