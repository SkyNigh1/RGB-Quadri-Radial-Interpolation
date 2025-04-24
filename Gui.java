import javax.swing.*;
import java.awt.*;

public class Gui {
    public static int taille = 63;
    // Déclaration du tableau de couleurs (modèle)
    private static ColorRGB[][] squareColors = new ColorRGB[taille][taille];

    // Tableau pour stocker les panneaux de chaque case
    private static JPanel[][] colorPanels = new JPanel[taille][taille];

    // Déclaration du panneau de la grille
    private static JPanel gridPanel;

    public static void createAndShowGui(ColorRGB topLeft, ColorRGB topRight, ColorRGB bottomLeft, ColorRGB bottomRight) {
        // Initialiser le tableau de couleurs
        initializeColors(topLeft, topRight, bottomLeft, bottomRight);

        // Créer et afficher l'interface Swing
        JFrame frame = new JFrame("Interpolation d'Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Créer un panneau avec un bouton et un panneau pour la grille
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Créer le panneau principal pour afficher la grille
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(taille, taille));

        // Remplir la grille avec des carrés colorés
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                JPanel colorPanel = new JPanel();
                colorPanel.setBackground(new Color(
                        squareColors[i][j].getRouge(),
                        squareColors[i][j].getVert(),
                        squareColors[i][j].getBleu()
                ));

                // Bordure supprimée ici
                // colorPanel.setBorder(blackBorder);

                colorPanels[i][j] = colorPanel;
                gridPanel.add(colorPanel);
            }
        }

        panel.add(gridPanel, BorderLayout.CENTER);

        JButton button = new JButton("Modifier une case");
        button.addActionListener(e -> {
            squareColors[10][10] = new ColorRGB(255, 0, 0);
            updateGui();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void initializeColors(ColorRGB topLeft, ColorRGB topRight, ColorRGB bottomLeft, ColorRGB bottomRight) {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i == 0 && j == 0) {
                    squareColors[i][j] = topLeft;
                } else if (i == 0 && j == taille - 1) {
                    squareColors[i][j] = topRight;
                } else if (i == taille - 1 && j == 0) {
                    squareColors[i][j] = bottomLeft;
                } else if (i == taille - 1 && j == taille - 1) {
                    squareColors[i][j] = bottomRight;
                } else {
                    squareColors[i][j] = new ColorRGB(255, 255, 255);
                }
            }
        }
    }

    public static void updateGui() {
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    public static void updateCell(int x, int y, ColorRGB newColor) {
        squareColors[x][y] = newColor;
        colorPanels[x][y].setBackground(new Color(
            newColor.getRouge(),
            newColor.getVert(),
            newColor.getBleu()
        ));
        updateGui();
    }

    public static ColorRGB getColorAt(int x, int y) {
        return squareColors[x][y];
    }
}
