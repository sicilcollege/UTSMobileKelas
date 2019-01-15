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

public class MasterBarangEdit extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    String Data = "";
    ArrayList<String[]> dataBarang = new ArrayList<String[]>();
    ListView lv_barang;

    EditText txtkode,txtnama,txtsatuan,txtstok,txtbeli,txtjual;
    Button btnsave,btnsaveedit,btndelete,btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterbarangedit);

        txtkode = (EditText)findViewById(R.id.txtkode);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txtsatuan = (EditText)findViewById(R.id.txtsatuan);
        txtstok = (EditText)findViewById(R.id.txtstok);
        txtbeli = (EditText)findViewById(R.id.txtbeli);
        txtjual = (EditText)findViewById(R.id.txtjual);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        Intent from_barang = getIntent();
        String[] detail_barang = from_barang.getStringArrayExtra("detail_barang");
        txtkode.setText(detail_barang[0]);
        txtnama.setText(detail_barang[1]);
        txtsatuan.setText(detail_barang[2]);
        txtstok.setText(detail_barang[3]);
        txtbeli.setText(detail_barang[4]);
        txtjual.setText(detail_barang[4]);

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
                Intent intent = new Intent(MasterBarangEdit.this, MasterBarang.class);
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
        txtsatuan.setText("");
        txtstok.setText("");
        txtbeli.setText("");
        txtjual.setText("");
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        }catch (Exception e){

        }
    }

    private void update(){
        try {
            db.execSQL("UPDATE MasterBarang SET NAMA = '"+txtnama.getText()+"', SATUAN = '"+txtsatuan.getText()+"', STOK = '"+txtstok.getText()+"', BELI = '"+txtbeli.getText()+"', JUAL = '"+txtjual.getText()+"' WHERE ID = '"+txtkode.getText()+"'");
            finish();
            Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Diubah!", Toast.LENGTH_SHORT).show();
        }
    }
    private void hapus(){
        try {
            db.delete("MasterBarang","ID=?", new String[]{txtkode.getText().toString()});
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