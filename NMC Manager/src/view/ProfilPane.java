package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Lists;
import model.Profil;

import com.jgoodies.forms.layout.FormLayout;

import controller.SocketManager;

/** Les panneaux utilisées pour le modification du profil
 * @author Derek & Antoine
 *
 */
public class ProfilPane extends JPanel implements ActionListener{
	private static final long serialVersionUID = -5634060463486776841L;
	private CommonUsed cu = new CommonUsed();
	private JPanel profilePane;
	
	private final FormLayout profileLayout = new FormLayout(
			"right:pref, 4dlu, fill:130dlu",
			"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, "
					+ "2dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref, 10dlu, pref");
	
	public ProfilPane(){
		cu.clear();
		cu.confirmButton.addActionListener(this);
		cu.modifyPassButton.addActionListener(this);
		cu.cancelButton.addActionListener(this);
		cu.modifyButton.addActionListener(this);
		profilePane = new JPanel();
		setProfilPane();
		initFields("profil");
		this.setLayout(new GridBagLayout());
		this.add(profilePane, new GridBagConstraints());
		profilePane.repaint(); profilePane.revalidate();
	}
	
	/** Initialise les champs de mot de passe
	 * @param type
	 */
	public void initFields(String type){
		if (type.equals("profil")){
			cu.passField.setText(null);
			cu.confirmPassField.setText(null);
			profilePane.add(cu.clearButton, cu.cc.xy(3,19));
		}
	}
	
	/**
	 * Définit le panneau de modification de profil
	 */
	private void setProfilPane() {
		initFields("profil");
		cu.userField.setText(Profil.getInstance().getUsername()); cu.userField.setEditable(false);
		cu.mailField.setText(Profil.getInstance().getMail()); cu.mailField.setEditable(false);
		cu.firstNameField.setText(Profil.getInstance().getFirstName()); cu.firstNameField.setEditable(false);
		cu.lastNameField.setText(Profil.getInstance().getLastName()); cu.lastNameField.setEditable(false);
		cu.birthField.setText(Profil.getInstance().getBirthdate()); cu.birthField.setEditable(false);
		cu.regField.setText(Profil.getInstance().getRegDate()); cu.regField.setEditable(false);
		cu.permField.setText(Lists.getInstance().returnLabel(Profil.getInstance().getPermissions_id())); cu.permField.setEditable(false);

		profileLayout.setRowGroups(new int[][]{{1, 3, 5, 7, 9, 11, 13, 15, 17, 19}});
		profilePane.setLayout(profileLayout);
		profilePane.add(cu.userLabel, cu.cc.xy(1, 1)); profilePane.add(cu.userField, cu.cc.xy(3,1));
		profilePane.add(cu.passLabel, cu.cc.xy(1, 3)); profilePane.add(cu.modifyPassButton, cu.cc.xy(3,3));
		profilePane.add(cu.mailLabel, cu.cc.xy(1, 7)); profilePane.add(cu.mailField, cu.cc.xy(3,7));
		profilePane.add(cu.firstNameLabel, cu.cc.xy(1, 9)); profilePane.add(cu.firstNameField, cu.cc.xy(3,9));
		profilePane.add(cu.lastNameLabel, cu.cc.xy(1, 11)); profilePane.add(cu.lastNameField, cu.cc.xy(3,11));
		profilePane.add(cu.birthLabel, cu.cc.xy(1, 13)); profilePane.add(cu.birthField, cu.cc.xy(3,13));
		profilePane.add(cu.regLabel, cu.cc.xy(1, 15)); profilePane.add(cu.regField, cu.cc.xy(3,15));
		profilePane.add(cu.permLabel, cu.cc.xy(1, 17)); profilePane.add(cu.permField, cu.cc.xy(3,17));
		profilePane.add(cu.modifyButton, cu.cc.xy(1, 19)); profilePane.add(cu.clearButton, cu.cc.xy(3,19));
		cu.modifyButton.setEnabled(false);
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
		} else if (e.getSource() == cu.confirmButton){
			if(String.valueOf(cu.passField.getPassword()).isEmpty() && String.valueOf(cu.confirmPassField.getPassword()).isEmpty()){
				JOptionPane.showMessageDialog(this,
						"Le nouveau mot de passe n'a pas été entré!",
						"Aucun mot de passe!",
						JOptionPane.ERROR_MESSAGE);
			} else if(!String.valueOf(cu.passField.getPassword()).equals(String.valueOf(cu.confirmPassField.getPassword()))){
				JOptionPane.showMessageDialog(this,
						"Les mots de passe ne correspondent pas",
						"Mauvais mot de passe!",
						JOptionPane.ERROR_MESSAGE);
			} else{
				if(String.valueOf(cu.passField.getPassword()).length() < 8){
					JOptionPane.showMessageDialog(this,
							"Les mots de passe doivent contenir au minimum 8 caractères",
							"Mauvais mot de passe!",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					cu.modifyButton.setEnabled(true);
					cu.passDialog.dispose();
				}

			}
		}  
		
	}
}
