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

import sicill.utsmobile.Adapter.SupplierAdapter;

import java.util.ArrayList;

public class MasterSupplier extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    ArrayList<String[]> dataSupplier = new ArrayList<String[]>();
    ListView lv_supplier;
    Button btnsave,btnsaveedit,btndelete,btnexit;
    EditText txtkode,txtnama,txttelp,txtalamat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mastersupplier);
        txtkode = (EditText)findViewById(R.id.txtkode);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txttelp = (EditText)findViewById(R.id.txttelp);
        txtalamat = (EditText)findViewById(R.id.txtalamat);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        lv_supplier = findViewById(R.id.lv_supplier);

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
            db.execSQL("CREATE TABLE IF NOT EXISTS MasterSupplier(ID VARCHAR PRIMARY KEY, NAMA VARCHAR,TELP VARCHAR,ALAMAT VARCHAR);");
        }catch (Exception e){

        }
    }

    private void get_data(){
        Cursor c = db.rawQuery("SELECT * FROM MasterSupplier", null);
        String a = "";
        dataSupplier.clear();
        while (c.moveToNext()){
            String[] ret = new String[4];
            ret[0] = c.getString(c.getColumnIndex("ID"));
            ret[1] = c.getString(c.getColumnIndex("NAMA"));
            ret[2] = c.getString(c.getColumnIndex("TELP"));
            ret[3] = c.getString(c.getColumnIndex("ALAMAT"));
            dataSupplier.add(ret);
        }
    }

    private void clearField(){
        txtkode.setText("");
        txtnama.setText("");
        txttelp.setText("");
        txtalamat.setText("");
    }

    private void simpan(){
        try {
            String sql = "INSERT INTO MasterSupplier VALUES('" + txtkode.getText() + "','"+txtnama.getText()+"','"+txttelp.getText()+"','"+txtalamat.getText()+"');";
            db.execSQL(sql);
            clearField();
            Toast.makeText(getApplicationContext(), "Data Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Disimpan!", Toast.LENGTH_SHORT).show();
        }
    }
    private void update(){
        try {
            db.execSQL("UPDATE MasterSupplier SET NAMA = '"+txtnama.getText()+"', TELP = '"+txttelp.getText()+"', ALAMAT = '"+txtalamat.getText()+"' WHERE ID = '"+txtkode.getText()+"'");
            finish();
            Toast.makeText(getApplicationContext(), "Data Berhasil Diubah!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Data Gagal Diubah!", Toast.LENGTH_SHORT).show();
        }
    }
    private void hapus(){
        try {
            db.delete("MasterSupplier","ID=?", new String[]{txtkode.getText().toString()});
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

        SupplierAdapter ka = new SupplierAdapter(getApplicationContext(), dataSupplier);
        lv_supplier.setAdapter(ka);
        lv_supplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getBaseContext(), MasterSupplierEdit.class);
                intent.putExtra("detail_supplier", dataSupplier.get(i));
                startActivity(intent);
                refreshpage();

            }
        });
    }

}
