package sicill.utsmobile;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import sicill.utsmobile.Adapter.BarangAdapter;

import java.util.ArrayList;

public class MasterBarang extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataBarang = new ArrayList<String[]>();
    ListView lv_barang;
    Button btnsave,btnsaveedit,btndelete,btnexit;
    EditText txtkode,txtnama,txtsatuan,txtstok,txtbeli,txtjual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterbarang);
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
        lv_barang = findViewById(R.id.lv_barang);

        btnsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                simpan();
                createDB();
                startActivity(getIntent());
                refreshpage();
                finish();
            }
        });

        btnsaveedit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                update();
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hapus();
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

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS MasterBarang(ID VARCHAR PRIMARY KEY," +
                    "NAMA VARCHAR," +
                    "SATUAN VARCHAR," +
                    "STOK VARCHAR," +
                    "BELI VARCHAR," +
                    "JUAL VARCHAR);");
        }catch (Exception e){

        }
    }

    private void get_data(){
        Cursor c = db.rawQuery("SELECT * FROM MasterBarang", null);
        String a = "";
        dataBarang.clear();
        while (c.moveToNext()){
            String[] ret = new String[6];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("SATUAN"));
            ret[3] = c.getString(c.getColumnIndex("STOK"));
            ret[4] = c.getString(c.getColumnIndex("BELI"));
            ret[5] = c.getString(c.getColumnIndex("JUAL"));
            dataBarang.add(ret);
        }
    }

    private void clearField(){
        txtkode.setText("");
        txtnama.setText("");
        txtsatuan.setText("");
        txtstok.setText("");
        txtbeli.setText("");
        txtjual.setText("");
    }

    private void simpan(){
        try {
            String sql = "INSERT INTO MasterBarang VALUES('" + txtkode.getText() + "','"+txtnama.getText()+"','"+txtsatuan.getText()+"','"+txtstok.getText()+"', '"+txtbeli.getText()+"', '"+txtbeli.getText()+"');";
            db.execSQL(sql);
            clearField();
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Disimpan!", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onStart() {
        super.onStart();
        createDB();
        get_data();
        btnsaveedit.setClickable(false);

        BarangAdapter ka = new BarangAdapter(getApplicationContext(), dataBarang);
        lv_barang.setAdapter(ka);
        lv_barang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getBaseContext(), MasterBarangEdit.class);
                intent.putExtra("detail_barang", dataBarang.get(i));
                startActivity(intent);
                refreshpage();

            }
        });
    }

}
