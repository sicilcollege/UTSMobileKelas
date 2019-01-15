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

import sicill.utsmobile.Adapter.KaryawanAdapter;

import java.util.ArrayList;

public class MasterKaryawan extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataKaryawan = new ArrayList<String[]>();
    ListView lv_karyawan;
    Button btnsave,btnsaveedit,btndelete,btnexit;
    EditText txtkode,txtnama,txttelp,txtalamat,txtpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masterkaryawan);
        txtkode = (EditText)findViewById(R.id.txtkode);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txttelp = (EditText)findViewById(R.id.txttelp);
        txtalamat = (EditText)findViewById(R.id.txtalamat);
        txtpassword = (EditText)findViewById(R.id.txtpassword);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        lv_karyawan = findViewById(R.id.lv_karyawan);

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
            db.execSQL("CREATE TABLE IF NOT EXISTS MasterKaryawan(ID VARCHAR PRIMARY KEY, NAMA VARCHAR,TELP VARCHAR,ALAMAT VARCHAR,PASSWORD VARCHAR);");
        }catch (Exception e){

        }
    }

    private void get_data(){
        Cursor c = db.rawQuery("SELECT * FROM MasterKaryawan", null);
        String a = "";
        dataKaryawan.clear();
        while (c.moveToNext()){
            String[] ret = new String[5];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("TELP"));
            ret[3] = c.getString(c.getColumnIndex("ALAMAT"));
            ret[4] = c.getString(c.getColumnIndex("PASSWORD"));
            dataKaryawan.add(ret);
        }
    }

    private void clearField(){
        txtkode.setText("");
        txtnama.setText("");
        txttelp.setText("");
        txtalamat.setText("");
        txtpassword.setText("");
    }

    private void simpan(){
        try {
            String sql = "INSERT INTO MasterKaryawan VALUES('" + txtkode.getText() + "','"+txtnama.getText()+"','"+txttelp.getText()+"','"+txtalamat.getText()+"', '"+txtpassword.getText()+"');";
            db.execSQL(sql);
            clearField();
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Disimpan!", Toast.LENGTH_SHORT).show();
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


    @Override
    public void onStart() {
        super.onStart();
        createDB();
        get_data();
        btnsaveedit.setClickable(false);

        KaryawanAdapter ka = new KaryawanAdapter(getApplicationContext(), dataKaryawan);
        lv_karyawan.setAdapter(ka);
        lv_karyawan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getBaseContext(), MasterKaryawanEdit.class);
                intent.putExtra("detail_karyawan", dataKaryawan.get(i));
                startActivity(intent);
                refreshpage();

            }
        });
    }

}
