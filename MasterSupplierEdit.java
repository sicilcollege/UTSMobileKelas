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

public class MasterSupplierEdit extends AppCompatActivity {

    private static String DBNAME = "UTS";
    SQLiteDatabase db;
    String Data = "";
    ArrayList<String[]> dataSupplier = new ArrayList<String[]>();
    ListView lv_supplier;

    EditText txtkode,txtnama,txttelp,txtalamat;
    Button btnsave,btnsaveedit,btndelete,btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mastersupplieredit);

        txtkode = (EditText)findViewById(R.id.txtkode);
        txtnama = (EditText)findViewById(R.id.txtnama);
        txttelp = (EditText)findViewById(R.id.txttelp);
        txtalamat = (EditText)findViewById(R.id.txtalamat);

        btnsave = findViewById(R.id.btnsave);
        btnsaveedit = findViewById(R.id.btnsaveedit);
        btndelete = findViewById(R.id.btndelete);
        btnexit = findViewById(R.id.btnexit);
        Intent from_barang = getIntent();
        String[] detail_supplier = from_barang.getStringArrayExtra("detail_supplier");
        txtkode.setText(detail_supplier[0]);
        txtnama.setText(detail_supplier[1]);
        txttelp.setText(detail_supplier[2]);
        txtalamat.setText(detail_supplier[3]);

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
                Intent intent = new Intent(MasterSupplierEdit.this, MasterSupplier.class);
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
    }

    private void createDB(){
        try {
            db = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        }catch (Exception e){

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
}