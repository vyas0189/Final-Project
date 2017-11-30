import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class AtmMachine extends JFrame {
    private JTextField accountNumberField, amountTextField, transferNumberField;
    private JButton loginBtn;
    private static JPasswordField passwordField;
    private Account myAccount;
    private String inputAccount, inputPass;

    private ArrayList<Account> accInfo = new ArrayList<>();
    private HashMap<String, String> login = new HashMap<>();
    private HashMap<String, Account> users = new HashMap<>();

    public static void main(String[] args) throws IOException {
        AtmMachine atm = new AtmMachine();
        atm.setVisible(true);
    }

    private AtmMachine() {
        initialize();
    }

    private void initialize() {
        readFile();
        setTitle("ATM Machine");
        setSize(600, 400);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel accountNumberLabel = new JLabel("Account number: ");
        accountNumberLabel.setBounds(175, 50, 100, 100);
        add(accountNumberLabel);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(210, 85, 100, 100);
        add(passwordLabel);

        accountNumberField = new JTextField();
        accountNumberField.setBounds(280, 90, 125, 25);

        add(accountNumberField);
        accountNumberField.setColumns(30);

        passwordField = new JPasswordField();
        passwordField.setBounds(280, 125, 125, 25);
        passwordField.setEchoChar('*');
        add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(200, 160, 100, 30);
        loginBtn.setBackground(Color.lightGray);
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputPass = new String(passwordField.getPassword());
                inputAccount = accountNumberField.getText();
                if (login != null && login.size() > 0 && inputPass.equals(login.get(inputAccount))) {
                    System.out.println("Login successful");
                    myAccount = users.get(inputAccount);

                    setVisible(false);
                    JFrame afterLog = new JFrame();
                    afterLog.setSize(400, 400);
                    afterLog.setTitle("Menu");
                    afterLog.setVisible(true);
                    afterLog.setLocationRelativeTo(null);

                    afterLog.setLayout(new GridLayout(3, 2));
                    JButton depositBtn = new JButton("Deposit");
                    depositBtn.setBackground(Color.green);
                    depositBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame depositFrame = new JFrame("Deposit");
                            depositFrame.setSize(500, 300);
                            depositFrame.setLayout(null);
                            depositFrame.setVisible(true);
                            depositFrame.setLocationRelativeTo(null);

                            JLabel amountLabel = new JLabel("Enter the amount: ");
                            amountLabel.setBounds(95, 100, 125, 30);
                            depositFrame.add(amountLabel);

                            amountTextField = new JTextField();
                            amountTextField.setBounds(200, 105, 90, 25);
                            depositFrame.add(amountTextField);

                            JButton submit = new JButton("Submit");
                            submit.setBounds(190, 135, 90, 25);
                            submit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    depositAmount(getAmount());
                                    depositFrame.dispose();
                                }
                            });
                            depositFrame.add(submit);
                        }
                    });
                    afterLog.add(depositBtn);

                    JButton withdrawBtn = new JButton("Withdraw");
                    withdrawBtn.setBackground(Color.LIGHT_GRAY);
                    withdrawBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame withdrawFrame = new JFrame("Withdraw");
                            withdrawFrame.setSize(500, 300);
                            withdrawFrame.setLayout(null);
                            withdrawFrame.setVisible(true);
                            withdrawFrame.setLocationRelativeTo(null);

                            JLabel amountLabel = new JLabel("Enter the amount: ");
                            amountLabel.setBounds(95, 100, 125, 30);
                            withdrawFrame.add(amountLabel);

                            amountTextField = new JTextField();
                            amountTextField.setBounds(200, 105, 90, 25);
                            withdrawFrame.add(amountTextField);

                            JButton submit = new JButton("Submit");
                            submit.setBounds(190, 135, 90, 25);
                            submit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    withdrawAmount(getAmount());
                                    withdrawFrame.dispose();
                                }
                            });
                            withdrawFrame.add(submit);
                        }
                    });
                    afterLog.add(withdrawBtn);

                    JButton checkBalBtn = new JButton("Check Balance");
                    checkBalBtn.setBackground(Color.lightGray);
                    checkBalBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, "You have " + myAccount.getBalance());
                        }
                    });
                    afterLog.add(checkBalBtn);

                    JButton makeTranBtn = new JButton("Make Transfer");
                    makeTranBtn.setBackground(Color.pink);
                    makeTranBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame transferFrame = new JFrame("Transfer");
                            transferFrame.setSize(500, 300);
                            transferFrame.setLayout(null);
                            transferFrame.setVisible(true);
                            transferFrame.setLocationRelativeTo(null);

                            JLabel transAcc = new JLabel("Enter the Account #: ");
                            transAcc.setBounds(95, 50, 125, 30);
                            transferFrame.add(transAcc);

                            transferNumberField = new JTextField();
                            transferNumberField.setBounds(210, 50, 90, 25);
                            transferFrame.add(transferNumberField);

                            JLabel amountLabel = new JLabel("Enter the amount: ");
                            amountLabel.setBounds(95, 100, 125, 30);
                            transferFrame.add(amountLabel);

                            amountTextField = new JTextField();
                            amountTextField.setBounds(210, 105, 90, 25);
                            transferFrame.add(amountTextField);

                            JButton submit = new JButton("Submit");
                            submit.setBounds(210, 135, 90, 25);
                            submit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    transferAmount(transferNumberField.getText(), getAmount());
                                    transferFrame.dispose();
                                }
                            });
                            transferFrame.add(submit);
                        }
                    });
                    afterLog.add(makeTranBtn);

                    JButton changePassBtn = new JButton("Change Password");
                    changePassBtn.setBackground(Color.orange);
                    changePassBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JFrame changePasswordFrame = new JFrame();
                            changePasswordFrame.setSize(300, 185);
                            changePasswordFrame.setLayout(null);
                            changePasswordFrame.setVisible(true);
                            changePasswordFrame.setLocationRelativeTo(null);

                            JLabel currentPasswordLabel = new JLabel("Current Password");
                            currentPasswordLabel.setBounds(10, 25, 105, 75);
                            changePasswordFrame.add(currentPasswordLabel);

                            JLabel changePasswordLabel = new JLabel("New Password");
                            changePasswordLabel.setBounds(28, 55, 105, 75);
                            changePasswordFrame.add(changePasswordLabel);

                            JTextField currentPasswordField = new JTextField();
                            currentPasswordField.setBounds(120, 52, 105, 20);
                            changePasswordFrame.add(currentPasswordField);

                            JTextField newPasswordField = new JTextField();
                            newPasswordField.setBounds(120, 85, 105, 20);
                            changePasswordFrame.add(newPasswordField);

                            JButton change = new JButton("Change");
                            change.setBounds(120, 110, 85, 30);
                            change.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    String currPass = currentPasswordField.getText();
                                    String newPass = newPasswordField.getText();
                                    changePassword(currPass, newPass);
                                    login.put(myAccount.getAccountNumber(), newPass);
                                    changePasswordFrame.dispose();
                                }
                            });
                            changePasswordFrame.add(change);
                        }
                    });
                    afterLog.add(changePassBtn);

                    JButton logoutBtn = new JButton("Logout");
                    logoutBtn.setBackground(Color.RED);
                    logoutBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            writeFile();
                            System.exit(0);
                        }
                    });
                    afterLog.add(logoutBtn);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong Username or Password");
                }
            }
        });
        add(loginBtn);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setBounds(350, 160, 100, 30);
        exitBtn.setBackground(Color.RED);
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exitBtn);
    }

    private void readFile() {
        try {
            Scanner fileAcc = new Scanner(new FileReader("AccountInformation.txt"));
            Scanner filePass = new Scanner(new FileReader("Password.txt"));

            while (fileAcc.hasNext()) {

                String accountNumber = fileAcc.nextLine();
                String lastName = fileAcc.nextLine();
                String firstName = fileAcc.nextLine();
                double balance = Double.valueOf(fileAcc.nextLine());
                String status = fileAcc.nextLine();
                users.put(accountNumber, new Account(accountNumber, lastName, firstName, balance, status));
            }

            while (filePass.hasNext()) {
                String inAccount = filePass.next();
                String inPass = filePass.next();
                login.put(inAccount, inPass);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"File not found");
            e.printStackTrace();
        }
    }

    public double getAmount() {
        try {
            double amount = Double.parseDouble(amountTextField.getText());
            return amount;
        } catch (NumberFormatException exception) {
            JOptionPane.showMessageDialog(this, "Please enter the amount!", "Number Format Error", JOptionPane.ERROR_MESSAGE);
            amountTextField.setText("");
            amountTextField.requestFocusInWindow();
        }
        return 0;
    }

    public void depositAmount(double amount) {
        myAccount.transfer(amount);
        JOptionPane.showMessageDialog(null, "Deposit successful");
    }

    public void withdrawAmount(double amount) {
        if (amount > myAccount.getBalance()) {
            JOptionPane.showMessageDialog(null, "You don't have enough money to withdraw");
        } else {
            myAccount.transfer(0 - amount);
            JOptionPane.showMessageDialog(null, "Withdraw successful");
        }
    }

    public String changePassword(String currPass, String newPass) {

        String inPass = login.get(myAccount.getAccountNumber());
        if (currPass.equals(inPass) && !(inPass.equals(" "))) {
            inPass = newPass;
            System.out.println("Changed Password");
            JOptionPane.showMessageDialog(null, "Password Changed");
        } else {
            inPass = inputPass;
            JOptionPane.showMessageDialog(null, "Current Password is incorrect");
        }
        return inPass;
    }

    public void transferAmount(String transferAccNum, double amount) {
        Account userReceiver = users.get(transferAccNum);
        Account userSender = users.get(myAccount.getAccountNumber());
        if (amount > userSender.getBalance()) {
            JOptionPane.showMessageDialog(null, "You don't have enough money to transfer");
        } else {
            userReceiver.transfer(amount);
            userSender.transfer(0 - amount);
            JOptionPane.showMessageDialog(null, "Transfer successful");
        }
    }

    public void writeFile() {
        try {
            BufferedWriter writeAcc = new BufferedWriter(new FileWriter("AccountInformation.txt"));
            BufferedWriter writePass = new BufferedWriter(new FileWriter("Password.txt"));

            for (String accountNumber : users.keySet()) {
                Account a = users.get(accountNumber);
                writeAcc.write(a.getAccountNumber() + "\n");
                writeAcc.write(a.getLastName() + "\n");
                writeAcc.write(a.getFirstName() + "\n");
                writeAcc.write(a.getBalance() + "\n");
                writeAcc.write(a.getStatus() + "\n");
            }
            for (String accountNumber : login.keySet()) {
                String b = login.get(accountNumber);
                writePass.write(accountNumber + " ");
                writePass.write(b + "\n");
            }
            writeAcc.close();
            writePass.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File not found");
        }
    }
}