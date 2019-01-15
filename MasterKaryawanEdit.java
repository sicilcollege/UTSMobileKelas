package sicill.utsmobile;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MasterKaryawanEdit extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    String Data = "";
    ArrayList<String[]> dataKaryawan = new ArrayList<String[]>();
    ListView lv_karyawan;

    EditText txtkode,txtnama,txttelp,txtalamat,txtpassword;
    Button btnsave,btnsaveedit,btndelete,btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterkaryawanedit);

        txtkode = (EditText)findViewById(R.id.txtkode);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txttelp = (EditText)findViewById(R.id.txttelp);
        txtalamat = (EditText)findViewById(R.id.txtalamat);
        txtpassword = (EditText)findViewById(R.id.txtpassword);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        Intent from_barang = getIntent();
        String[] detail_karyawan = from_barang.getStringArrayExtra("detail_karyawan");
        txtkode.setText(detail_karyawan[0]);
        txtnama.setText(detail_karyawan[1]);
        txttelp.setText(detail_karyawan[2]);
        txtalamat.setText(detail_karyawan[3]);
        txtpassword.setText(detail_karyawan[4]);

        txtkode.setEnabled(false);
        createDB();

        btnsaveedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
                refreshpage();
            }
        });


        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus();
                Intent intent = new Intent(MasterKaryawanEdit.this, MasterKaryawan.class);
                startActivity(intent);
                refreshpage();

            }
        });
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                refreshpage();
            }
        });
    }

    private void clearField(){
        txtkode.setText("");
        txtnama.setText("");
        txttelp.setText("");
        txtalamat.setText("");
        txtpassword.setText("");
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        }catch (Exception e){

        }
    }

    private void update(){
        try {
            db.execSQL("UPDATE MasterKaryawan SET NAMA = '"+txtnama.getText()+"', TELP = '"+txttelp.getText()+"', ALAMAT = '"+txtalamat.getText()+"', PASSWORD = '"+txtpassword.getText()+"' WHERE ID = '"+txtkode.getText()+"'");
            finish();
            Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Diubah!", Toast.LENGTH_SHORT).show();
        }
    }
    private void hapus(){
        try {
            db.delete("MasterKaryawan","ID=?", new String[]{txtkode.getText().toString()});
            finish();
            Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
        }
    }
    private void refreshpage(){
        overridePendingTransition(0, 0);
    }
}