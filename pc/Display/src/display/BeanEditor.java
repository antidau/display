package display;

import display.scene.Scene;
import java.awt.*;
import java.beans.*;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

public class BeanEditor extends JFrame implements PropertyChangeListener {
    
    protected Scene m_bean;
    protected JTable m_table;
    protected PropertyTableData m_data;
    
    public BeanEditor(Scene bean) {
        
        m_bean = bean;

        //TODO: uncomment
        //m_bean.addPropertyChangeListener(this);

        
        
        getContentPane().setLayout(new BorderLayout());
        
        m_data = new PropertyTableData(m_bean);
        
        m_table = new JTable(m_data);
        
        JScrollPane ps = new JScrollPane();
        
        ps.getViewport().add(m_table);
        
        setSize(400, 300);
        
        getContentPane().add(ps, BorderLayout.CENTER);
        
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        setVisible(true);
        
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        
        m_data.setProperty(evt.getPropertyName(), evt.getNewValue());
        
    }
    
    class PropertyTableData extends AbstractTableModel {
        
        protected String[][] m_properties;
        protected int m_numProps = 0;
        protected Vector m_v;
        
        public PropertyTableData(Scene bean) {
            
            try {
                
                BeanInfo info = Introspector.getBeanInfo(
                        m_bean.getClass());
                
                BeanDescriptor descr = info.getBeanDescriptor();
                
                setTitle("Editing " + descr.getName());
                
                PropertyDescriptor[] props = info.getPropertyDescriptors();
                
                m_numProps = props.length;
                
                m_v = new Vector(m_numProps);
                
                for (int k = 0; k < m_numProps; k++) {
                    
                    String name = props[k].getDisplayName();
                    
                    boolean added = false;
                    
                    for (int i = 0; i < m_v.size(); i++) {
                        
                        String str = ((PropertyDescriptor) m_v.elementAt(i)).getDisplayName();
                        
                        if (name.compareToIgnoreCase(str) < 0) {
                            
                            m_v.insertElementAt(props[k], i);
                            
                            added = true;
                            
                            break;
                            
                        }
                        
                    }
                    
                    if (!added) {
                        m_v.addElement(props[k]);
                    }
                    
                }
                
                m_properties = new String[m_numProps][2];
                
                for (int k = 0; k < m_numProps; k++) {
                    
                    PropertyDescriptor prop =
                            (PropertyDescriptor) m_v.elementAt(k);
                    
                    m_properties[k][0] = prop.getDisplayName();
                    
                    Method mRead = prop.getReadMethod();
                    
                    if (mRead != null
                            && mRead.getParameterTypes().length == 0) {
                        
                        Object value = mRead.invoke(m_bean, null);
                        
                        m_properties[k][1] = objToString(value);
                        
                    } else {
                        m_properties[k][1] = "error";
                    }
                    
                }
                
            } catch (Exception ex) {
                
                ex.printStackTrace();
                
                JOptionPane.showMessageDialog(
                        BeanEditor.this, "Error: " + ex.toString(),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                
            }
            
        }
        
        public void setProperty(String name, Object value) {
            
            for (int k = 0; k < m_numProps; k++) {
                if (name.equals(m_properties[k][0])) {
                    
                    m_properties[k][1] = objToString(value);
                    
                    m_table.tableChanged(new TableModelEvent(this, k));
                    
                    m_table.repaint();
                    
                    break;
                    
                }
            }
            
        }
        
        public int getRowCount() {
            return m_numProps;
        }
        
        public int getColumnCount() {
            return 2;
        }
        
        public String getColumnName(int nCol) {
            
            return nCol == 0 ? "Property" : "Value";
            
        }
        
        public boolean isCellEditable(int nRow, int nCol) {
            
            return (nCol == 1);
            
        }
        
        public Object getValueAt(int nRow, int nCol) {
            
            if (nRow < 0 || nRow >= getRowCount()) {
                return "";
            }
            
            switch (nCol) {
                
                case 0:
                    return m_properties[nRow][0];
                
                case 1:
                    return m_properties[nRow][1];
                
            }
            
            return "";
            
        }
        
        public void setValueAt(Object value, int nRow, int nCol) {
            
            if (nRow < 0 || nRow >= getRowCount()) {
                return;
            }
            
            String str = value.toString();
            
            PropertyDescriptor prop = (PropertyDescriptor) m_v.elementAt(nRow);
            
            Class cls = prop.getPropertyType();
            
            Object obj = stringToObj(str, cls);
            
            if (obj == null) {
                return;        // can't process
            }
            Method mWrite = prop.getWriteMethod();
            
            if (mWrite == null || mWrite.getParameterTypes().length != 1) {
                return;
            }
            
            try {
                
                mWrite.invoke(m_bean, new Object[]{obj});
            } catch (Exception ex) {
                
                ex.printStackTrace();
                
                JOptionPane.showMessageDialog(
                        BeanEditor.this, "Error: " + ex.toString(),
                        "Warning", JOptionPane.WARNING_MESSAGE);
                
            }
            
            m_properties[nRow][1] = str;
            
        }
        
        public String objToString(Object value) {
            
            if (value == null) {
                return "null";
            }
            
            if (value instanceof Dimension) {
                
                Dimension dim = (Dimension) value;
                
                return "" + dim.width + "," + dim.height;
                
            } else if (value instanceof Insets) {
                
                Insets ins = (Insets) value;
                
                return "" + ins.left + "," + ins.top + "," + ins.right + "," + ins.bottom;
                
            } else if (value instanceof Rectangle) {
                
                Rectangle rc = (Rectangle) value;
                
                return "" + rc.x + "," + rc.y + "," + rc.width + "," + rc.height;
                
            } else if (value instanceof Color) {
                
                Color col = (Color) value;
                
                return "" + col.getRed() + "," + col.getGreen() + "," + col.getBlue();
                
            }
            
            return value.toString();
            
        }
        
        public Object stringToObj(String str, Class cls) {
            
            try {
                
                if (str == null) {
                    return null;
                }
                
                String name = cls.getName();
                
                if (name.equals("java.lang.String")) {
                    return str;
                } else if (name.equals("int")) {
                    return new Integer(str);
                } else if (name.equals("long")) {
                    return new Long(str);
                } else if (name.equals("float")) {
                    return new Float(str);
                } else if (name.equals("double")) {
                    return new Double(str);
                } else if (name.equals("boolean")) {
                    return new Boolean(str);
                } else if (name.equals("java.awt.Dimension")) {
                    
                    int[] i = strToInts(str);
                    
                    return new Dimension(i[0], i[1]);
                    
                } else if (name.equals("java.awt.Insets")) {
                    
                    int[] i = strToInts(str);
                    
                    return new Insets(i[0], i[1], i[2], i[3]);
                    
                } else if (name.equals("java.awt.Rectangle")) {
                    
                    int[] i = strToInts(str);
                    
                    return new Rectangle(i[0], i[1], i[2], i[3]);
                    
                } else if (name.equals("java.awt.Color")) {
                    
                    int[] i = strToInts(str);
                    
                    return new Color(i[0], i[1], i[2]);
                    
                }
                
                return null;    // not supported

            } catch (Exception ex) {
                return null;
            }
            
        }
        
        public int[] strToInts(String str) throws Exception {
            
            int[] i = new int[4];
            
            StringTokenizer tokenizer = new StringTokenizer(str, ",");
            
            for (int k = 0; k < i.length
                    && tokenizer.hasMoreTokens(); k++) {
                i[k] = Integer.parseInt(tokenizer.nextToken());
            }
            
            return i;
            
        }
    }
}
