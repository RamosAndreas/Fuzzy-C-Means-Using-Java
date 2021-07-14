import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;


public class App {
    public static void main(String[] args) {
        JFrame appframe = new JFrame("App");
        appframe.setContentPane(new App().Panel);
        appframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appframe.pack();
        appframe.setVisible(true);
    }

    public JTextField tfData;
    private JTextField tfBobot;
    private JTextField tfIterasi;
    private JTextField tfEpsilon;
    private JButton lanjutButton;
    private JButton keluarButton;
    private JPanel Panel;
    private JTextField tf1;
    private JTextField tfnilaierror;
    private JTextField tfIterasiakhir;
    private JTextField utf1;
    private JTextField utf2;
    private JTextField utf3;
    private JTextField utf4;
    private JTextField utf5;
    private JTextField utf6;
    private JTextField utf7;
    private JTextField utf8;
    private JTextField utf9;
    private JTextField utf10;
    private JTextField tf2;
    private JTextField tf3;
    private JTextField tf4;
    private JTextField tf5;
    private JTextField tf6;
    private JTextField tf7;
    private JTextField tf8;
    private JTextField tf9;
    private JTextField tf10;

    //Membangkitkan uik
    public static double[][] cetakangkarandom() {
        //Untuk memunculkan angka random antara (0,1)
        Random angkarandom = new Random();
        double[][] randomU = new double[5][2];
        //Membentuk angka random ke bentuk matriks dengan jumlah tiap baris bernilai 1
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 1; j++) {
                while (true) {
                    float a = angkarandom.nextFloat();
                    float b = angkarandom.nextFloat();
                    if (a + b == 1) {
                        randomU[i][j] = a;
                        randomU[i][j + 1] = b;
                        break;
                    }
                }
            }
        }
        return randomU;
    }

    //Fungsi menghitung uik dipangkatkan dengan bobot
    public static double[][] uikpangkatw(int bobot, double[][] Urandom){
        double[][] pangkatbobot = new double[5][2];
        for (int i=0; i<5; i++){
            for (int j=0; j<2; j++){
                pangkatbobot[i][j] = Math.pow(Urandom[i][j],bobot);
            }
        }
        return pangkatbobot;
    }

    /*Fungsi mengalikan uik yagn sudah dipangkatkan dengan bobot ke
    xij atau data.*/
    public static double[][] pusatcluster(int bobot, double[][] Urandom, int[] di){
        double[][] upangkat = uikpangkatw(bobot, Urandom);
        double[][] upangkatkalitiapdata = new double[5][4];
        double[] jumlahupangkat = new double[2];
        double[] jumlahupangkatkalitiapdata = new double[4];
        double[][] pcluster = new double[2][2];
        int x;
        int y = 0;
        int z = 0;
        int p = 0;

        for (int i = 0; i < 2; i++) {
            x = 0;
            for (int j = 0; j < 2; j++) {
                for (int k=0; k < 5; k++){
                    upangkatkalitiapdata[k][y] = upangkat[k][z]*di[x];
                    x++;
                }y++;
            }
            z++;
        }

        for (int i = 0; i<4; i++){
            for (int j = 0; j<5; j++){
                jumlahupangkatkalitiapdata[i] += upangkatkalitiapdata[j][i];
            }
        }

        for (int i = 0; i<2; i++){
            for (int j = 0; j<5; j++){
                jumlahupangkat[i] += upangkat[j][i];
            }
        }

        for (int i = 0; i<2; i++) {
            for (int j = 0; j < 2; j++) {
                pcluster[i][j] = jumlahupangkatkalitiapdata[p]/jumlahupangkat[i];
                p++;
            }
        }
        return pcluster;
    }

    //Fungsi hitung nilai fungsi objektif
    public static double fobjktf(int bobot, double[][] Urandom, int[] di, double[][] pstcluster){
        double[][] upgktw = uikpangkatw(bobot, Urandom);
        double[][] datakurangpusat = new double[5][4];
        double[][] c = new double[5][2];
        double fob = 0;
        int l;
        int m = 0;
        int n = 0;
        int o = 1;
        int r = 0;
        int s = 0;
        int t = 0;
        int u = 1;
        //Menghitung C1 dan C2
        for (int i = 0; i < 2; i++) {
            l = 0;
            for (int j = 0; j < 2; j++) {
                for (int k=0; k < 5; k++){
                    datakurangpusat[k][m] = Math.pow(di[l]-pstcluster[n][j],2);
                    l++;
                }m++;
            }n++;
        }
        for (int j = 0; j<2; j++){
            for (int k = 0; k<5; k++) {
                c[k][j] = (datakurangpusat[k][j+r] + datakurangpusat[k][j+o])*upgktw[k][j];
            }o++;r++;
        }
        //Menghitung fungsi objektif
        for (int i = 0; i<5; i++){
            fob += c[i][t] + c[i][u];
        }
        return fob;
    }

    //Fungsi update derajat keanggotaan
    public static double[][] derajatkeanggotaan(int bobot, int[] di, double[][] pstcluster){
        int l;
        int m = 0;
        int n = 0;
        int g = 1;
        int h = 0;
        double[][] datakrgpst = new double[5][4];
        double[][] lt = new double[5][1];
        double[][] ubaru = new double[5][2];

        for (int i = 0; i < 2; i++) {
            l = 0;
            for (int j = 0; j < 2; j++) {
                for (int k=0; k < 5; k++){
                    datakrgpst[k][m] = Math.pow(di[l]-pstcluster[n][j],bobot);
                    l++;
                }m++;
            }n++;
        }

        for (int i = 0; i<5; i++){
            for (int j = 0; j<4; j++){
                lt[i][0] += datakrgpst[i][j];
            }
        }

        //Cluster1&2
        for (int j = 0; j<2; j++){
            for (int k = 0; k<5; k++) {
                ubaru[k][j] = (datakrgpst[k][j+h] + datakrgpst[k][j+g])/lt[k][0];
            }g++;h++;
        }
        return ubaru;
    }

    public App() {
        keluarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        lanjutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Deklarasi variabel parameter
                int bobot = Integer.parseInt(tfBobot.getText());
                float epsilon = Float.parseFloat(tfEpsilon.getText());
                int iterasi = Integer.parseInt(tfIterasi.getText());
                double fawal = 0;
                double temp;
                double[][] Urandom = cetakangkarandom();
                double[][] uikbaru;

                //Mengambil data input
                int[] datainput = new int[10];
                datainput[0]=Integer.parseInt(tf1.getText());
                datainput[1]=Integer.parseInt(tf2.getText());
                datainput[2]=Integer.parseInt(tf3.getText());
                datainput[3]=Integer.parseInt(tf4.getText());
                datainput[4]=Integer.parseInt(tf5.getText());
                datainput[5]=Integer.parseInt(tf6.getText());
                datainput[6]=Integer.parseInt(tf7.getText());
                datainput[7]=Integer.parseInt(tf8.getText());
                datainput[8]=Integer.parseInt(tf9.getText());
                datainput[9]=Integer.parseInt(tf10.getText());

                //Menghitung pusat cluster
                double[][] pstcluster = pusatcluster(bobot, Urandom, datainput);

                //Fungsi objektif
                double fungsiobjektif = fobjktf(bobot, Urandom, datainput, pstcluster);

                for (int i = 0; i<iterasi; i++){
                    //Update derajat keanggotaan
                    uikbaru = derajatkeanggotaan(bobot, datainput, pstcluster);
                    if (Math.abs(fungsiobjektif-fawal)!=epsilon && Math.abs(fungsiobjektif-fawal)<epsilon) {
                        utf1.setText(String.valueOf(uikbaru[0][0]));
                        utf2.setText(String.valueOf(uikbaru[1][0]));
                        utf3.setText(String.valueOf(uikbaru[2][0]));
                        utf4.setText(String.valueOf(uikbaru[3][0]));
                        utf5.setText(String.valueOf(uikbaru[4][0]));
                        utf6.setText(String.valueOf(uikbaru[0][1]));
                        utf7.setText(String.valueOf(uikbaru[1][1]));
                        utf8.setText(String.valueOf(uikbaru[2][1]));
                        utf9.setText(String.valueOf(uikbaru[3][1]));
                        utf10.setText(String.valueOf(uikbaru[4][1]));
                        tfnilaierror.setText(String.valueOf(Math.abs(fungsiobjektif-fawal)));
                        tfIterasiakhir.setText(String.valueOf(i+1));
                        break;
                    }
                    fawal = fungsiobjektif;
                    pstcluster = pusatcluster(bobot, uikbaru, datainput);
                    fungsiobjektif = fobjktf(bobot, uikbaru, datainput, pstcluster);
                }
            }
        });
    }
}