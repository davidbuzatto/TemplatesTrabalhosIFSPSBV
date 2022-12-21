/**
 * Classe que implementa um exemplo do Java2D.
 *
 * @author David Buzatto
 */
public class Java2D extends JFrame {
    
    public static void main( String[] args ) {
        Java2D j2d = new Java2D();
        j2d.setTitle( "Exemplo do Java2D" );
        j2d.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        j2d.add( new Painel2D() );
        j2d.setSize( 300, 300 );
        j2d.setVisible( true );
    }
    
}

class Painel2D extends JPanel {
    
    public void paintComponent( Graphics g ) {    
        super.paintComponent( g );
        
        Graphics2D g2d = ( Graphics2D ) g;
        
        // desenha uma elipse
        Shape elipse = new Ellipse2D.Double( 50, 40, 180, 180 );
        
        // cria um gradiente para pintar a elipse
        GradientPaint paint = new GradientPaint( 50, 50, Color.WHITE, 
                250, 250, Color.GRAY );
        
        // seta o gradiente como o paint para o graphics 2D
        g2d.setPaint( paint );
        
        // desenha com preenchimento a elipse no graphics 2D
        g2d.fill( elipse );
        
        // configura a transparência
        AlphaComposite ac = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.4f );
        
        g2d.setComposite( ac );
        
        // configura a cor para verde
        g2d.setColor( Color.RED );
        
        // configura a fonte
        Font font = new Font( "Serif" , Font.BOLD, 40 );
        g2d.setFont(font);

        // desenha o texto transparente
        g2d.drawString( "Olá Java 2D!", 30, 140 );
    }
    
}