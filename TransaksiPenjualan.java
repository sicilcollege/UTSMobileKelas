package sicill.utsmobile;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import sicill.utsmobile.Adapter.BarangAdapter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TransaksiPenjualan extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;

    ArrayList<String[]> dataCustomer = new ArrayList<String[]>();
    ArrayList<String[]> dataBarang = new ArrayList<String[]>();
    String[] tmpBarang;
    ArrayList<String[]> dataTransaksi = new ArrayList<String[]>();
    ArrayList<String[]> dataDetailBarang = new ArrayList<String[]>();
    Double grandTotal = 0.0;
    int nm_trans = 0;
    Boolean cek = true;
    int min_jml = 0;


    EditText txtid,txtcustomer, txtidcustomer, date, time, txtBarang, txtIdBarang, txtjumlah;
    Button   btn_trans_simpan, btn_tambah_barang;
    TextView txttotal;
    TableLayout tbl_det_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaksipenjualan);
        txtid = (EditText)findViewById(R.id.txtid);
        txtcustomer = (EditText)findViewById(R.id.txtcustomer);
        txtidcustomer = (EditText)findViewById(R.id.txtidcustomer);
        txtBarang = (EditText)findViewById(R.id.txtBarang);
        txtIdBarang = (EditText)findViewById(R.id.txtIdBarang);
        txtjumlah = (EditText)findViewById(R.id.txtjumlah);

        btn_trans_simpan = (Button)findViewById(R.id.btn_trans_simpan);
        btn_tambah_barang = (Button)findViewById(R.id.btn_tambah_barang);

        txttotal = (TextView)findViewById(R.id.txttotal);

        tbl_det_barang = (TableLayout)findViewById(R.id.tbl_det_barang);

        createDB();
        get_data();
        gen_id_trans();

        String myDate =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        date = (EditText)findViewById(R.id.date);
        date.setText(myDate);

        txtBarang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                show_barang();
            }
        });
        btn_tambah_barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_barang();
            }
        });

        btn_trans_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });


    }

    private void clearField(){
        txtid.setText("");
        txtcustomer.setText("");
        txtidcustomer.setText("");
        txtBarang.setText("");
        txttotal.setText("0");
    }

    private void gen_id_trans(){
        String num = "00"+ String.valueOf(nm_trans);
        String nt = "TJ"+num.substring(num.length()-2, num.length());
        txtid.setText(nt);
        Log.e("GEN - ", num);
        Log.e("GEN - ", nt);
    }

    private void show_barang(){
        get_data_barang();
        BarangAdapter ca = new BarangAdapter(getApplicationContext(), dataBarang);
        AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPenjualan.this);
        builder.setTitle("Pilih Barang")
                .setAdapter(ca, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(Integer.parseInt(dataBarang.get(i)[2])==0){
                            cek = false;
                        }else{
                            min_jml = Integer.parseInt(dataBarang.get(i)[2]);
                            txtBarang.setText(dataBarang.get(i)[1]);
                            txtIdBarang.setText(dataBarang.get(i)[0]);
                            tmpBarang = dataBarang.get(i);
                        }
                    }
                });
        builder.create().show();
        if(!cek){
            AlertDialog.Builder bb = new AlertDialog.Builder(TransaksiPenjualan.this);
            bb.setMessage("Stok habis!")
                    .setNegativeButton("ok", null).create().show();
        }
    }

    private void add_barang(){
        String tbarang = txtIdBarang.getText().toString();
        String tjml = txtjumlah.getText().toString();

        if(tbarang.equals("") || tjml.equals("") || tjml.equals("0")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPenjualan.this);
            builder.setMessage("Silahkan pilih barang atau isikan jumlah")
                    .setNegativeButton("Retry", null).create().show();
        }else if(Integer.parseInt(tjml)>min_jml){
            AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPenjualan.this);
            builder.setMessage("Stok tidak mencukupi")
                    .setNegativeButton("Retry", null).create().show();
        }else{
            Double hrg = Double.parseDouble(tmpBarang[4]);
            int jml = Integer.parseInt(String.valueOf(txtjumlah.getText()));
            Double subtotal = hrg*jml;

            String[] brg = new String[5];
            brg[0] = tmpBarang[0];
            brg[1] = tmpBarang[1];
            brg[2] = String.valueOf(jml);
            brg[3] = tmpBarang[4];
            brg[4] = String.valueOf(subtotal);
            dataDetailBarang.add(brg);
            refresh_list();
            txtIdBarang.setText("");
            txtBarang.setText("");
            txtjumlah.setText("");
        }
    }

    private void refresh_list(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        int co = tbl_det_barang.getChildCount();
        for(int i = 1; i<co; i++){
            View child = tbl_det_barang.getChildAt(i);
            if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
        }
        grandTotal = 0.0;
        for(int i=0;i<dataDetailBarang.size();i++){
            TableRow row = new TableRow(TransaksiPenjualan.this);
            TextView tid = new TextView(TransaksiPenjualan.this);
            TextView tno = new TextView(TransaksiPenjualan.this);
            TextView tna = new TextView(TransaksiPenjualan.this);
            TextView tjm = new TextView(TransaksiPenjualan.this);
            TextView thg = new TextView(TransaksiPenjualan.this);
            TextView tot = new TextView(TransaksiPenjualan.this);

            Double hrg = Double.parseDouble(dataDetailBarang.get(i)[3]);
            Double st = Double.parseDouble(dataDetailBarang.get(i)[4]);
            grandTotal += st;


            tid.setText(dataDetailBarang.get(i)[0]);
            tno.setText(String.valueOf(i+1));
            tna.setText(dataDetailBarang.get(i)[1]);
            tjm.setText(dataDetailBarang.get(i)[2]);
            thg.setText(formatRupiah.format((double)hrg));
            tot.setText(formatRupiah.format((double)st));

            row.addView(tid);
            row.addView(tno);
            row.addView(tna);
            row.addView(tjm);
            row.addView(thg);
            row.addView(tot);
            tbl_det_barang.addView(row);
        }
        txttotal.setText(formatRupiah.format((double)grandTotal));
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
            String sql_trans = "CREATE TABLE IF NOT EXISTS Penjualan (" +
                    "ID_TRANSAKSI VARCHAR PRIMARY KEY, " +
                    "TGL_TRANSAKSI DATE, " +
                    "CUSTOMER VARCHAR, " +
                    "TOTAL DOUBLE);";
            String sql_det_trans = "CREATE TABLE IF NOT EXISTS DetailPenjualan (" +
                    "ID_DET_TRANSAKSI INT PRIMARY KEY, " +
                    "ID_TRANSAKSI VARCHAR, " +
                    "ID_BARANG VARCHAR, " +
                    "JUMLAH INT, " +
                    "HARGA DOUBLE, " +
                    "SUBTOTAL DOUBLE);";
            String sql_trigger = "CREATE TRIGGER IF NOT EXISTS t_penjualan AFTER INSERT ON DetailPenjualan " +
                    "BEGIN " +
                    "UPDATE masterbarang SET stok = stok - new.stok WHERE _id = new._id; " +
                    "END;";
            db.execSQL(sql_trans);
            db.execSQL(sql_det_trans);
            db.execSQL(sql_trigger);
        }catch (Exception e){
            Log.e("SQL ERROR", "TERJADI KELASALAHAN");

        }
    }

    private void get_data_barang(){
        String sql = "SELECT * FROM MasterBarang";
        Cursor c = db.rawQuery(sql, null);
        String a = "";
        dataBarang.clear();
        while (c.moveToNext()){
            String[] ret = new String[6];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("STOK"));
            ret[3] = c.getString(c.getColumnIndex("SATUAN"));
            ret[4] = c.getString(c.getColumnIndex("BELI"));
            ret[5] = c.getString(c.getColumnIndex("JUAL"));
            dataBarang.add(ret);
        }
    }

//    private void get_data_customer(){
//        Cursor c = db.rawQuery("SELECT * FROM mastercustomer", null);
//        String a = "";
//        dataCustomer.clear();
//        while (c.moveToNext()){
//            String[] ret = new String[3];
//            ret[0] = c.getString(c.getColumnIndex("_id"));
//            ret[1] = c.getString(c.getColumnIndex("nama"));
//            ret[2] = c.getString(c.getColumnIndex("telp"));
//            dataCustomer.add(ret);
//        }
//    }

    private void get_data(){
        Cursor c = db.rawQuery("SELECT * FROM penjualan", null);
        nm_trans = c.getCount()+1;
    }

    private void simpan(){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        if(txtid.getText().toString().equals("") ||
                date.getText().toString().equals("") ||
                tbl_det_barang.getChildCount() == 1
                ){
            AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPenjualan.this);
            builder.setMessage("Silahkan Lengkapi data!")
                    .setNegativeButton("Retry", null).create().show();
        }else{
            String msg = "";
            msg += "ISEEU MARKET\n";
            msg += "Jl Stikom Surabaya \n";
            msg += "=============================================\n";

            String sql = "INSERT INTO Penjualan VALUES(" +
                    "'"+txtid.getText()+"',"+
                    "'"+date.getText()+"',"+
                    "'"+txtcustomer.getText()+"',"+
                    ""+grandTotal+""+
                    ");";
            try{
                db.execSQL(sql);
                msg += "Tanggal : "+date.getText().toString()+"\n";
                msg += "Customer : "+txtcustomer.getText().toString()+"\n";
                msg += "=============================================\n";
                msg += "Barang \t jml \t Hrg \t Total\n";
                msg += "---------------------------------------------\n";
                for(int i=0;i<dataDetailBarang.size();i++){
                    String sql_det = "INSERT INTO DetailPenjualan VALUES(" +
                            "'"+txtid.getText()+dataDetailBarang.get(i)[0]+"', "+
                            "'"+txtid.getText()+"', "+
                            "'"+dataDetailBarang.get(i)[0]+"', "+
                            ""+dataDetailBarang.get(i)[2]+", "+
                            ""+dataDetailBarang.get(i)[3]+", "+
                            ""+dataDetailBarang.get(i)[4]+"); ";
                    try{
                        db.execSQL(sql_det);

                        Double hrg = Double.parseDouble(dataDetailBarang.get(i)[3]);
                        Double st = Double.parseDouble(dataDetailBarang.get(i)[4]);
                        msg += dataDetailBarang.get(i)[1]+" \t "+dataDetailBarang.get(i)[2]+" \t "+formatRupiah.format((double)hrg)+" \t "+formatRupiah.format((double)st)+"\n";
                    }catch (Exception e){
                        Log.e("DETAIL EROOR", "GAGAL DITAMBAH!");
                        Log.e("DETAIL EROOR", sql_det);
                    }
                }
                msg += "---------------------------------------------\n";
                Double gt = grandTotal;
                msg += "\t\t\t\t Total : "+formatRupiah.format((double)gt)+"\n";
                msg += "\t\t\t\t Grand Total : "+formatRupiah.format((double)grandTotal)+"\n";

                msg += "\n\n\n\t\t***** Terimakasih Atas Kunjungan Anda *****\n";
                Log.e("NOTA", msg);
                finish();
            }catch (Exception e){
                AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiPenjualan.this);
                builder.setMessage("Terjadi Kesalahan, Data Transaksi Gagal Disimpan!")
                        .setNegativeButton("Retry", null).create().show();
            }
        }

    }
}

