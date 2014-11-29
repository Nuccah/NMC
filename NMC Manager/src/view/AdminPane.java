package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Lists;
import model.NMCTableModel;
import model.Permissions;
import model.Profil;

import com.jgoodies.forms.layout.FormLayout;

import controller.Crypter;
import controller.SocketManager;

public class AdminPane extends JPanel implements ActionListener{
	private static final long serialVersionUID = 6053063954351925886L;
	private CommonUsed cu = new CommonUsed();
	private JPanel createUserPane = new JPanel();
	private final FormLayout userLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref,");
	private static final String[] userColumns = new String[]{"Nom d'utilisateur", "Prénom", "Nom", "Email", "Date de naissance", "Date de création", "Rang"};
	private static final String[] permissionColumns = new String[]{"Nom de rang", "Niveau"};
	
	public AdminPane(String tab){
		cu.addButton.addActionListener(this);
		cu.modifyPassButton.addActionListener(this);
		cu.cancelButton.addActionListener(this);
		cu.modifyButton.addActionListener(this);
		setComponentLists();
		setCreateUserPane();
		cu.clear();
		this.setLayout(new GridBagLayout());
		cu.node = tab;
		if (cu.node.equals(cu.userNode)){
			createUserPane.removeAll();
			userLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}});
			createUserPane.setLayout(userLayout);
			this.add(createUserPane, new GridBagConstraints());
			setCreateUserPane();
		}
		else if (cu.node.equals(cu.adminNode)){
			JPanel admin_pane = new JPanel(); 
			admin_pane.removeAll();
			try {
				SocketManager.getInstance().getList("users");
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this,
						"Impossible de récupérer la liste des utilisateurs."
								+ "Veuillez relancer l'application",
								"Erreur de mise à jour",
								JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			}
			cu.vmc.weightx = 1; cu.vmc.weighty = 1; cu.vmc.fill = GridBagConstraints.BOTH;
			admin_pane.add(createTable("users"), cu.vmc);
			this.add(admin_pane, cu.vmc);
		}
		else if (cu.node.equals(cu.permNode)){
			JPanel perm_pane = new JPanel();
			perm_pane.removeAll();
			try {
				SocketManager.getInstance().getList("permissions");
			} catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this,
						"Impossible de récupérer la liste des permissions."
								+ "Veuillez relancer l'application",
								"Erreur de mise à jour",
								JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
			} 
			perm_pane.add(createTable("permissions"));
			this.add(perm_pane, new GridBagConstraints());
		}
	}
	
	private JScrollPane createTable(String type) {
		try {
			SocketManager.getInstance().getList(type);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(this,
					"Impossible de récupérer la liste mise à jour après l'ajout d'une série ou d'un album."
							+ "Veuillez relancer l'application",
							"Erreur de mise à jour",
							JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		JTable table = null;
		switch (type){
		case "users":
			table = new JTable(new NMCTableModel(Lists.getInstance().getUsersList(), userColumns));
			break;
		case "permissions":
			table = new JTable(new NMCTableModel(Lists.getInstance().getPermissionsList(), permissionColumns));;
			break;
		}
		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		// Add the scroll pane to this panel.
		return scrollPane;
	}
	
	/** Determines whether  metadata fields are empty or not
	 * @param node the String of the node selected
	 * @return boolean whether metadata fields are empty or not
	 */
	public boolean verify(){
		if(cu.userField.getText().equals("") || cu.passField.getPassword().equals("") || cu.confirmPassField.getPassword().equals("") || cu.mailField.getText().equals("")
				|| cu.firstNameField.getText().equals("") || cu.lastNameField.getText().equals("") || cu.birthField.getText().equals("") 
				|| cu.permissionsBox.getSelectedItem() == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	private void setComponentLists() {
		cu.fieldList.add(cu.passField);
		cu.fieldList.add(cu.confirmPassField);
		cu.fieldList.add(cu.mailField);
		cu.fieldList.add(cu.userField);
		cu.fieldList.add(cu.firstNameField);
		cu.fieldList.add(cu.lastNameField);
		cu.fieldList.add(cu.birthField);
		cu.fieldList.add(cu.permLevelField);

		cu.cbPList.add(cu.modificationBox);
		cu.cbPList.add(cu.visibilityBox);
		cu.cbPList.add(cu.permissionsBox);
	}
	
	private void setCreateUserPane(){
		cu.userField.setEditable(true);
		cu.mailField.setEditable(true);
		cu.firstNameField.setEditable(true);
		cu.lastNameField.setEditable(true);
		cu.birthField.setEditable(true);
		cu.regField.setEditable(true);
		cu.permField.setEditable(true);
		createUserPane.add(cu.userLabel, cu.cc.xy(1, 1)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,1)); createUserPane.add(cu.userField, cu.cc.xy(3,1));
		createUserPane.add(cu.passLabel, cu.cc.xy(1, 3)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,3)); createUserPane.add(cu.passField, cu.cc.xy(3,3));
		createUserPane.add(cu.confirmPassLabel, cu.cc.xy(1, 5)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,5)); createUserPane.add(cu.confirmPassField, cu.cc.xy(3,5));
		createUserPane.add(cu.mailLabel, cu.cc.xy(1, 7)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,7)); createUserPane.add(cu.mailField, cu.cc.xy(3,7));
		createUserPane.add(cu.firstNameLabel, cu.cc.xy(1, 9)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,9)); createUserPane.add(cu.firstNameField, cu.cc.xy(3,9));
		createUserPane.add(cu.lastNameLabel, cu.cc.xy(1, 11)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,11)); createUserPane.add(cu.lastNameField, cu.cc.xy(3,11));
		createUserPane.add(cu.birthLabel, cu.cc.xy(1, 13)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,13)); createUserPane.add(cu.birthField, cu.cc.xy(3,13));
		createUserPane.add(cu.permLabel, cu.cc.xy(1, 15)); createUserPane.add(new JLabel("*"), cu.cc.xy(2,15)); createUserPane.add(cu.permissionsBox, cu.cc.xy(3,15));
		createUserPane.add(new JLabel("* = champs requis"), cu.cc.xy(3,17));
		createUserPane.add(cu.addButton, cu.cc.xy(1, 19)); createUserPane.add(cu.clearButton, cu.cc.xy(3,19));
		createUserPane.revalidate(); 
		createUserPane.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cu.modifyButton){
			if(SocketManager.getInstance().modifyUser(String.valueOf(cu.passField.getPassword())))
				JOptionPane.showMessageDialog(this,
						"Le mot de passe a été changé avec succès!",
						"Modifications effectuées",
						JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this,
						"Le mot de passe n'a pas été changé!",
						"Modifications annulées",
						JOptionPane.ERROR_MESSAGE);
			cu.modifyButton.setEnabled(false);
		} else if(e.getSource() == cu.modifyPassButton){
			cu.clear();
			cu.setPassDialog();
		} else if (e.getSource() == cu.cancelButton){
			cu.passDialog.dispose();
			cu.modifyButton.setEnabled(false);
		} else if(e.getSource() == cu.addButton){
				if (!verify()){
					JOptionPane.showMessageDialog(this,
							"Tous les champs requis n'ont pas été rempli",
							"Pas asssez de données",
							JOptionPane.ERROR_MESSAGE);
				}
				else if(!String.valueOf(cu.passField.getPassword()).equals(String.valueOf(cu.confirmPassField.getPassword()))){
					JOptionPane.showMessageDialog(this,
							"Passwords do not match",
							"Bad Credentials",
							JOptionPane.ERROR_MESSAGE);
				}
				else if(cu.passField.getPassword().length < 8){
					JOptionPane.showMessageDialog(this,
							"Passwords must be at least 8 characters",
							"Bad Credentials",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					long sql = 0;
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date parsed = format.parse(cu.birthField.getText());
				        sql = parsed.getTime();
					} catch (ParseException e1) {
						JOptionPane.showMessageDialog(this,
								"La date doit être au format jj/MM/AAAA",
								"Mauvaise date",
								JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
						return;
					}
					Profil tmp = new Profil(cu.userField.getText(), Crypter.encrypt(cu.passField.getPassword().toString()), cu.mailField.getText(), cu.firstNameField.getText(),
							cu.lastNameField.getText(), new java.sql.Date(sql), ((Permissions)cu.permissionsBox.getSelectedItem()).getId());
					if(SocketManager.getInstance().createUser(tmp))
						JOptionPane.showMessageDialog(this,
								"Un nouveau utilisateur a été crée!",
								"Ajout effectué",
								JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(this,
								"Un nouveau utilisateur n'a pas pu etre crée",
								"Echec",
								JOptionPane.WARNING_MESSAGE);
				}
		}
		
	}
}
