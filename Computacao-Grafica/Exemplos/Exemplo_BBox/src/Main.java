/// \file Exemplo_N2_Jogl_Eclipse.java
/// \brief Exemplo_N2_Jogl_Eclipse: desenha uma linha na diagonal.
/// \version $Revision: 1.0 $
/// \author Dalton Reis.
/// \date 03/05/13.
/// Obs.: variaveis globais foram usadas por questoes didaticas mas nao sao recomendas para aplicacoes reais.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class Main implements GLEventListener, KeyListener {
	private GL gl;
	private GLU glu;
	private GLAutoDrawable glDrawable;
	private Point4D pto1 = new Point4D(100.0, 100.0, 0.0, 1.0);
	private Point4D pto2 = new Point4D(200.0, 200.0, 0.0, 1.0);
	
	private BoundingBox bBox = new BoundingBox(pto1.GetX(),pto1.GetY(),pto1.GetZ(),pto1.GetX(),pto1.GetY(),pto1.GetZ());

	public void init(GLAutoDrawable drawable) {
		System.out.println(" --- init ---");
		glDrawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		glDrawable.setGL(new DebugGL(gl));
		System.out.println("Espaco de desenho com tamanho: " + drawable.getWidth() + " x " + drawable.getHeight());
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		bBox.atualizarBBox(pto2);
		bBox.processarCentroBBox();
	}
	
	//exibicaoPrincipal
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		glu.gluOrtho2D(-400.0f, 400.0f, -400.0f, 400.0f);

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();

		SRU();
		 
		 // seu desenho ...
		 gl.glColor3f(0.0f, 0.0f, 0.0f);
		 gl.glLineWidth(3.0f);
		 gl.glBegin(GL.GL_LINES);
		 	gl.glVertex2d(pto1.GetX(),pto1.GetY());
		    gl.glVertex2d(pto2.GetX(),pto2.GetY());
		 gl.glEnd();

		 bBox.desenharOpenGLBBox(gl);
		    gl.glColor3f(0.0f, 1.0f, 1.0f);
		    gl.glPointSize(3.0f);
		    gl.glBegin(GL.GL_POINTS);
		        gl.glVertex2d(bBox.obterCentro().GetX(),bBox.obterCentro().GetY());
		    gl.glEnd();		 
		 
		 gl.glFlush();
	}	

	private void recalculaBBox () {
		bBox.atribuirBoundingBox(pto1.GetX(), pto1.GetY(), pto1.GetZ(), pto2.GetX(), pto2.GetY(), pto2.GetZ());
		bBox.atualizarBBox(pto2);
		bBox.processarCentroBBox();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q: // teclas esquerda
            pto1.SetX(pto1.GetX()-1);
            recalculaBBox();
    		glDrawable.display();
			break;
		case KeyEvent.VK_W: // teclas direita
            pto1.SetX(pto1.GetX()+1);
            recalculaBBox();
    		glDrawable.display();
			break;
		}
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		System.out.println(" --- reshape ---");
	    gl.glMatrixMode(GL.GL_PROJECTION);
	    gl.glLoadIdentity();
		gl.glViewport(0, 0, width, height);
	}

	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		System.out.println(" --- displayChanged ---");
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println(" --- keyReleased ---");
	}

	public void keyTyped(KeyEvent arg0) {
		System.out.println(" --- keyTyped ---");
	}
	
	public void SRU() {
//		gl.glDisable(gl.GL_TEXTURE_2D);
//		gl.glDisableClientState(gl.GL_TEXTURE_COORD_ARRAY);
//		gl.glDisable(gl.GL_LIGHTING); //TODO: [D] FixMe: check if lighting and texture is enabled

		// eixo x
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glLineWidth(1.0f);
		gl.glBegin( GL.GL_LINES );
			gl.glVertex2f( -200.0f, 0.0f );
			gl.glVertex2f(  200.0f, 0.0f );
			gl.glEnd();
		// eixo y
		gl.glColor3f(0.0f, 1.0f, 0.0f);
		gl.glBegin( GL.GL_LINES);
			gl.glVertex2f(  0.0f, -200.0f);
			gl.glVertex2f(  0.0f, 200.0f );
		gl.glEnd();
	}

}
