package lucas.client.service.pos.admin.financeiro;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.READ_MEDIA_VIDEO;

import android.Manifest;
import android.content.*;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.*;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import lucas.client.service.pos.admin.*;
import lucas.client.service.pos.admin.etc.*;
import lucas.client.service.pos.admin.sqlite.*;

import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.Settings;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.*;
import lucas.client.service.pos.admin.financeiro.adapter.*;
import android.widget.AdapterView.*;
import android.view.*;

public class Fechamento extends AppCompatActivity
{
	Context c = this;
	private static final int PERMISSION_REQUEST_CODE = 200;
	private static final String TAG = "PERMISSION_TAG";
	String supR1, supR2, supR3, supR4, supR5, supR6, SupResult,
			sanR1, sanR2, sanR3, sanR4, sanR5, sanR6, sanResult,
	        soma1, soma2, soma3, soma4, soma5, soma6;
	List<util> lt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fechamento);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		SQLiteControl db = new SQLiteControl(c);
		lt = db.fechamento();
		ListView l = (ListView) findViewById(R.id.list);
		l.setAdapter(new fechamentoAdapter(this, lt));
		l.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, final int p3, long p4)
				{


					// TODO: Implement this method
					if(checkPermission()){
						File f = new File(Environment.getExternalStorageDirectory(), "pdvMain/data/lucas.client.service/.fechamentos/fechamento_" + lt.get(p3).getData() + ".html");
						if (f.exists()){
							LayoutInflater li = getLayoutInflater();
							View r = li.inflate(R.layout.fechamento_viewer, null);
							final WebView wb =  r.findViewById(R.id.web);
							wb.setWebViewClient(new WebViewClient());

							File root = new File(Environment.getExternalStorageDirectory(), "pdvMain/data/lucas.client.service/.fechamentos/fechamento_" + lt.get(p3).getData() + ".html");
							Uri url = Uri.fromFile(root);
							wb.getSettings().setAllowFileAccess(true);
							wb.getSettings().setAllowFileAccessFromFileURLs(true);
							wb.loadUrl(url.toString());

							AlertDialog.Builder al = new AlertDialog.Builder(c);
							al.setTitle("Fechamento: " + lt.get(p3).getData());
							al.setView(r);
							al.setNeutralButton("Imprimir", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
									createWebPrintJob(wb);
								}
							});
							al.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {

								}
							});
							al.setNegativeButton("Cancelar", null);
							al.create();
							al.show();
						} else {
							AlertDialog.Builder alert = new AlertDialog.Builder(c);
							alert.setTitle("Gerar Nova NFe:");
							alert.setMessage("O arquivo NFe não existe ou foi excluído do seu dispositivo. \n " +
									         "Para visualizar a NFe selecionada um novo documento será criado. Deseja Continuar?");
							alert.setPositiveButton("Continuar", new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface dialog, int which) {
									if(checkPermission()){
										try {
											File root = new File(Environment.getExternalStorageDirectory(), "pdvMain/data/lucas.client.service/.fechamentos/fechamento_" + lt.get(p3).getData() +".html");
											FileWriter fw = new FileWriter(root);
											fw.write(lt.get(p3).getNfehtml());
											fw.flush();
											fw.close();
											Toast.makeText(c, "NFe criada com sucesso!", Toast.LENGTH_SHORT).show();
										} catch(IOException e){

										}
									} else {
										Toast.makeText(c, "Você conceder acesso ao armazenamento do seu dispositivo para gravar arquivos.", Toast.LENGTH_SHORT).show();
										requestPermission();
									}
								}
							});
							alert.setNegativeButton("Cancelar", null);
							alert.create();
							alert.show();
						}
					} else {
						Toast.makeText(c, "Sem Permissão", Toast.LENGTH_LONG).show();
					}
				}
		});
		
	}
	private void createWebPrintJob(WebView webView) {

		// Get a PrintManager instance
		PrintManager printManager = (PrintManager) c.getSystemService(Context.PRINT_SERVICE);

		String jobName = getString(R.string.app_name) + " Document";

		// Get a print adapter instance
		PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

		// Create a print job with name and adapter instance
		PrintJob printJob = printManager.print(jobName, printAdapter,
				new PrintAttributes.Builder().build());

		// Save the job object for later status checking

	}
	private void requestPermission(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
			try{

				Intent it = new Intent();
				it.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
				Uri uri = Uri.fromParts("package", this.getPackageName(), null);
				it.setData(uri);
				storageActivityResultLauncher.launch(it);
			}catch (Exception e){

				Intent it2 = new Intent();
				it2.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
				storageActivityResultLauncher.launch(it2);
			}
		} else {
			ActivityCompat.requestPermissions(Fechamento.this, new String[]{WRITE_EXTERNAL_STORAGE, MANAGE_EXTERNAL_STORAGE,  READ_EXTERNAL_STORAGE, READ_MEDIA_AUDIO, READ_MEDIA_IMAGES, READ_MEDIA_VIDEO},  PERMISSION_REQUEST_CODE);
		}
	}
	private ActivityResultLauncher<Intent> storageActivityResultLauncher =
			registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
					new ActivityResultCallback<ActivityResult>(){

						@Override
						public void onActivityResult(ActivityResult o) {
							if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
								//Android is 11 (R) or above
								if(Environment.isExternalStorageManager()){
									//Manage External Storage Permissions Granted

									Log.d(TAG, "onActivityResult: Manage External Storage Permissions Granted");
								}else{
									Toast.makeText(Fechamento.this, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
								}
							}else{
								//Below android 11

							}
						}
					});

	public boolean checkPermission(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
			return Environment.isExternalStorageManager();
		} else {
			int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
			int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

			return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
		}

	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode == PERMISSION_REQUEST_CODE){
			if(grantResults.length > 0){
				boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
				boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

				if(read && write){
					Toast.makeText(Fechamento.this, "Storage Permissions Granted", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(Fechamento.this, "Storage Permissions Denied", Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
}
