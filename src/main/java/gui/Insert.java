package gui;

import hibernate.Goods;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.*;

public class Insert {

    public void createTable(){
        Color yellow = new Color(170,134,58);
        Color orange = new Color(147,83,71);
        Color white = new Color(240,236,235);

        JFrame frame2 = new JFrame("Insert Goods");
        frame2.setBackground(white);
        frame2.setSize(900, 500);
        //frame2.setContentPane(getBrEntryPage());
        frame2.setVisible(true);

        final JTable table = new JTable();

        Object[] columns = {"Id","Name","Price","Count","Made In","Supplier"};
        final DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);

        table.setBackground(white);
        table.setForeground(yellow);
        Font font = new Font("Century Gothic", 1, 22);
        table.setFont(font);
        table.setRowHeight(30);

        final JLabel lblName = new JLabel();
        final JTextField textName = new JTextField();

        final JLabel lblPrice= new JLabel();
        final JTextField textPrice = new JTextField();

        final JLabel lblCount= new JLabel();
        final JTextField textCount = new JTextField();

        final JLabel lblMadeIn= new JLabel();
        final JTextField textMadeIn= new JTextField();

        final JLabel lblSupplier = new JLabel();
        final JTextField textSupplier = new JTextField();

        final JLabel lblSearch = new JLabel();
        final JTextField txtSearch = new JTextField();

        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnUpdate = new JButton("Update");

        btnAdd.setBackground(yellow);
        btnAdd.setForeground(Color.white);

        btnUpdate.setBackground(yellow);
        btnUpdate.setForeground(Color.white);

        btnDelete.setBackground(orange);
        btnDelete.setForeground(Color.white);

        lblName.setForeground(yellow);
        lblPrice.setForeground(yellow);
        lblCount.setForeground(yellow);
        lblMadeIn.setForeground(yellow);
        lblSupplier.setForeground(yellow);
        lblSearch.setForeground(yellow);

        lblName.setText("Name : ");
        lblPrice.setText("Price : ");
        lblCount.setText("Count : ");
        lblMadeIn.setText("Made In : ");
        lblSupplier.setText("Supplier : ");

        lblName.setBounds(20, 220, 80, 25);
        textName.setBounds(100, 220, 170, 25);

        lblPrice.setBounds(310, 220, 80, 25);
        textPrice.setBounds(370, 220, 80, 25);

        lblCount.setBounds(310, 250, 80, 25);
        textCount.setBounds(370, 250, 80, 25);

        lblMadeIn.setBounds(500, 220, 80, 25);
        textMadeIn.setBounds(580, 220, 100, 25);

        lblSupplier.setBounds(500, 250, 80, 25);
        textSupplier.setBounds(580, 250, 100, 25);

        lblSearch.setBounds(20, 280, 80, 25);
        txtSearch.setBounds(100, 280, 170,25);

        btnAdd.setBounds(20, 300, 100, 25);
        btnUpdate.setBounds(150, 300, 100, 25);
        btnDelete.setBounds(280, 300, 100, 25);

        // create JScrollPane
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 0, 880, 200);
        pane.setBackground(white);

        frame2.setLayout(null);

        frame2.add(pane);

        // add JTextFields to the jframe
        frame2.add(lblName);
        frame2.add(lblPrice);
        frame2.add(lblCount);
        frame2.add(lblMadeIn);
        frame2.add(lblSupplier);

        frame2.add(textName);
        frame2.add(textPrice);
        frame2.add(textCount);
        frame2.add(textMadeIn);
        frame2.add(textSupplier);

        frame2.add(btnAdd);
        frame2.add(btnDelete);
        frame2.add(btnUpdate);

        frame2.setBackground(white);


        final Object[] row = new Object[7];
        long id = 0;
        String name = null;
        String price = null;
        String count = null;
        String madeIn = null;
        String supplier = null;

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session se = sf.openSession();
        se.beginTransaction();
        final org.hibernate.query.Query qry = se.createQuery("from Goods");
        List<Goods> goodsInfo = (List<Goods>) qry.list();
        se.getTransaction().commit();
        se.close();
        for(Goods  d: goodsInfo)
        {

            id = d.getId();
            name = d.getName();
            price = String.valueOf(d.getPrice());
            count = String.valueOf(d.getCount());
            madeIn = d.getMadeIn();
            supplier = d.getSupplier();

            row[0] = id;
            row[1] = name;
            row[2] = price;
            row[3] = count;
            row[4] = madeIn;
            row[5] = supplier;
            // add row to the model
            model.addRow(row);

}


        table.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent e){

                int i = table.getSelectedRow();

                textName.setText(model.getValueAt(i, 1).toString());
                textPrice.setText(model.getValueAt(i, 2).toString());
                textCount.setText(model.getValueAt(i, 3).toString());
                textMadeIn.setText(model.getValueAt(i, 4).toString());
                textSupplier.setText(model.getValueAt(i, 5).toString());
            }
        });

        btnAdd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session s = sf.openSession();
                s.beginTransaction();
                Goods newGoods = new Goods();
                newGoods.setName(textName.getText());
                newGoods.setPrice(Double.parseDouble( textPrice.getText()));
                newGoods.setCount(Integer.parseInt(textCount.getText()));
                newGoods.setMadeIn(textMadeIn.getText());
                newGoods.setSupplier(textSupplier.getText());

                s.save(newGoods);
                s.getTransaction().commit();
                s.close();

                JOptionPane.showMessageDialog(null, "Goods "+textName.getText()+" successfully added!");
                JComponent comp = (JComponent) e.getSource();
                Window win = SwingUtilities.getWindowAncestor(comp); //get top window
                win.dispose();
                createTable();

            }

        });
        btnDelete.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session se = sf.openSession();
                System.out.println(table.getSelectedColumn());

                Transaction transaction = se.beginTransaction();
                try {

                    String hql = "delete from Goods where id= :id";
                    Query query = se.createQuery(hql);

                    query.setParameter("id", table.getModel().getValueAt(table.getSelectedRow(), 0));

                    System.out.println(query.executeUpdate());

                    transaction.commit();

                    JOptionPane.showMessageDialog(null, "Goods "+textName.getText()+" successfully deleted!");
                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp); //get top window
                    win.dispose();
                    createTable();

                } catch (Throwable t) {
                    transaction.rollback();
                    throw t;
                }

            }
        });


        btnUpdate.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("1 done !");
                SessionFactory sf = HibernateUtil.getSessionFactory();
                Session se = sf.openSession();
                System.out.println("2 done !");

                Transaction transaction = se.beginTransaction();
                System.out.println("3 done !");
                try {

                    System.out.println("4 done !");
                    String hql = "update Goods set name= :name,price= :price,count= :count, madeIn= :madeIn, supplier= :supplier where id= :id";
                    System.out.println("5 done !");
                    Query queryX = se.createQuery(hql);
                    System.out.println("6 done !");
                    queryX.setParameter("name", textName.getText());
                    queryX.setParameter("price", textPrice.getText());
                    queryX.setParameter("count", textCount.getText());
                    queryX.setParameter("madeIn", textMadeIn.getText());
                    queryX.setParameter("supplier", textSupplier.getText());
                    queryX.setParameter("id", table.getModel().getValueAt(table.getSelectedRow(), 0));
                    System.out.println("7 done !");



                    int rowCount = qry.executeUpdate();
                    System.out.println("Rows affected: " + rowCount);


                    transaction.commit();
                } catch (Throwable t) {
                    transaction.rollback();
                    throw t;
                }



                int i = table.getSelectedRow();

                if(i >= 0)
                {
                    model.setValueAt(textName.getText(), i, 1);
                    model.setValueAt(textPrice.getText(), i, 2);
                    model.setValueAt(textCount.getText(), i, 3);
                    model.setValueAt(textMadeIn.getText(), i, 4);
                    model.setValueAt(textSupplier.getText(), i, 5);

                    JOptionPane.showMessageDialog(null, "Item "+textName.getText()+" successfully updaded!");
                }
                else{
                    System.out.println("Update Error");
                }
            }
        });



        frame2.setSize(900,400);
        frame2.setLocationRelativeTo(null);

        frame2.setVisible(true);
}

}
