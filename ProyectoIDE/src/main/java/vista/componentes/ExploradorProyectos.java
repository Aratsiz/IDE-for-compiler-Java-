package vista.componentes;

import java.awt.Color;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.Component;


public class ExploradorProyectos extends JTree {
    public ExploradorProyectos() {
        configurarApariencia();
        setModel(crearModeloEjemplo());
    }

    private void configurarApariencia() {
        setBackground(new Color(255, 240, 230));
        setCellRenderer(new RendererArbolBonsai());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
    }

    private DefaultTreeModel crearModeloEjemplo() {
        DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("プロジェクト (Proyecto)");
        DefaultMutableTreeNode src = new DefaultMutableTreeNode("源 (src)");
        raiz.add(src);
        src.add(new DefaultMutableTreeNode("Main.刀"));
        raiz.add(new DefaultMutableTreeNode("資源 (resources)"));
        return new DefaultTreeModel(raiz);
    }

    // Renderer personalizado
    private static class RendererArbolBonsai extends DefaultTreeCellRenderer {
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, 
                                                     boolean expanded, boolean leaf, int row, 
                                                     boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            setIcon(leaf ? new ImageIcon("recursos/icons/hoja.png") 
                         : new ImageIcon("recursos/icons/ramas.png"));
            return this;
        }
    }
}