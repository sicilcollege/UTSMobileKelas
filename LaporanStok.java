package sicill.utsmobile;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import sicill.utsmobile.Adapter.AdapterStok;

public class LaporanStok extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static String DBNAME = "UTS";
    EditText tglawalStok, tglakhirStok;
    Button btn_cari;
    ListView LVStock;
    String [] arraynamabarang, arraystokawal, arraystokbeli, arraystokjual, arraystokakhir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laporanstoklistview);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        tglawalStok = (EditText)findViewById(R.id.tgl_awal);
        tglakhirStok = (EditText)findViewById(R.id.tgl_akhir);
        btn_cari = (Button) findViewById(R.id.btn_cari);
        LVStock = (ListView)findViewById(R.id.list_laporanstok);

        SQLiteDatabase db = openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
        Cursor c = db.rawQuery("SELECT B.NAMA as nama, B.STOK as stok, DB.JUMLAH as stokbeli, DJ.JUMLAH as stokjual,(B.STOK+DB.JUMLAH-DJ.JUMLAH) as stokakhir FROM DetailPembelian DB join MasterBarang B on (B.ID = DB.ID_BARANG) join DetailPenjualan DJ on (B.ID = DJ.ID_BARANG) group by B.NAMA, B.STOK, DB.JUMLAH, DJ.JUMLAH,(B.STOK+DB.JUMLAH-DJ.JUMLAH) order by B.NAMA ASC ",null);
                c.moveToFirst();

        arraynamabarang = new String[c.getCount()];
        arraystokawal = new String[c.getCount()];
        arraystokjual = new String[c.getCount()];
        arraystokbeli = new String[c.getCount()];
        arraystokakhir = new String[c.getCount()];

        int i = 0;
        while (c.moveToNext()) {
            String namabarang = c.getString(c.getColumnIndex("nama"));
            String stokawal = c.getString(c.getColumnIndex("stok"));
            String stokbeli = c.getString(c.getColumnIndex("stokbeli"));
            String stokjual = c.getString(c.getColumnIndex("stokjual"));
            String stokakhir = c.getString(c.getColumnIndex("stokakhir"));

            arraynamabarang[i] = namabarang;
            arraystokawal[i] = stokawal;
            arraystokjual[i] = stokjual;
            arraystokbeli[i] = stokbeli;
            arraystokjual[i] = stokakhir;
            i = i +1;
            AdapterStok adapter = new AdapterStok(getApplicationContext(), arraynamabarang, arraystokawal, arraystokjual, arraystokbeli, arraystokakhir);
            LVStock.setAdapter(adapter);
        }
        c.close();

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}