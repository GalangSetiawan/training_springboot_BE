package co.id.sofcograha.base.constants;

import java.text.SimpleDateFormat;

public class BaseConstants {
  protected static final String TOKEN = "/token";
  public static final String PUBLIC = "/public";
  public static final String PRIVATE = "/api";
  public static final String AUTHENTICATION_TOKEN_REQUEST = PUBLIC + TOKEN + "/request";
  public static final String AUTHENTICATION_TOKEN_SWITCH = PRIVATE + TOKEN + "/switch";
  //public static final String PRIVATE = "/private";
  public static final String AUTHENTICATION_LOGOUT = PRIVATE + TOKEN + "/logout";
  public static final String KEY_TOKEN = "token";
  
  public static final String ID = "/{id:.+}";
  
	public static final String EMPTY = "";
	public static final String BLANK = " ";
	public static final String DUMMY_COS_ID = "PT001";
	public static final String NOTNULL = "NOTNULL";
	
	public static final String AUTHORIZATION_HEADER = "X-Auth-Token";
	public static final String MENU_CODE = "Menu-Code";
	public static final String AUTH_ERROR_CODE = "CORE.AUTHO.999";
	public static final String BLOCKED_PATH_ERROR_CODE = "CORE.BLOCK.999";
	public static final String DEFAULT_MESSAGE_DESC = "";
	
	public static final String STATUS_OK = "ok";
	public static final String STATUS_WARNING = "warning";
	public static final String STATUS_ERROR = "error";
	
	//TODO: SimpleDateFormat is not thread safe!
	public static final SimpleDateFormat JSON_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	
	public static final String PAGINATION = "pagination";
	public static final int DEFAULT_QUERY_DEPTH = 2;
	public static final int DEFAULT_DETAIL_QUERY_DEPTH = 0;
	public static final int MIN_IMAGE_SIZE_BYTES = 2;
	public static final int MAX_REAL_IMAGE_SIZE_IN_BYTES = 4096 * 1024;
	public static final int MAX_LOGO_SIZE_IN_BYTES = 1024 * 1024;
	public static final int THUMBNAIL_DEFAULT_WIDTH = 350;
	public static final String XLS_FILE_MIME_PATTERN = "data:application/vnd.ms-excel;base64,.+";
	public static final String[] ALLOWED_IMAGE_TYPE = { "image/png", "image/x-png", "image/jpg", "image/jpeg" };
	
  public static final String COA_AKTIVA_ID = "1000";
  public static final String COA_PASSIVA_ID = "2000";
  public static final String COA_KEWAJIBAN_ID = "3000";
  public static final String COA_MODAL_ID = "4000";

  public static final String KEL_PENDAPATAN = "C";
  public static final String KEL_BIAYA = "D";

  public static final String DEBET = "D";
  public static final String KREDIT = "K";
  
  public static final String YA = "Y";
  public static final String TIDAK = "T";
  
  
  public static final String ERROR_DEFAULT_NAME = "errorDefaultName";
  public static final String WARNING_DEFAULT_NAME = "warningDefaultName";
  
  public static final String DATE_FORMAT = "yyyyMMdd";
  public static final String DATE_FORMAT_DISPLAY_LONG = "dd-MM-yyyy HH:mm:ss";
  
	// dipakai di excel
	public static final String HORIZONTAL_ALIGN_CENTER = "CENTER";
	public static final String HORIZONTAL_ALIGN_LEFT = "LEFT";
	public static final String HORIZONTAL_ALIGN_RIGHT = "RIGHT";
	public static final String VERTICAL_ALIGN_BOTTOM = "BOTTOM";
	public static final String VERTICAL_ALIGN_CENTER = "CENTER";
	public static final String VERTICAL_ALIGN_TOP = "TOP";
	public static final String BORDER_STYLE_DASHED = "DASHED";
	public static final String BORDER_STYLE_DOTTED = "DOTTED";
	public static final String BORDER_STYLE_DOUBLE = "DOUBLE";
	public static final String BORDER_STYLE_MEDIUM = "MEDIUM";
	public static final String BORDER_STYLE_THICK = "THICK";
	public static final String BORDER_STYLE_THIN = "THIN";
	
	// dipakai di jenis penomoran otomatis
	public static final String TIPE_KOMPONEN_JENIS_TRANSAKSI = "1";
	public static final String TIPE_KOMPONEN_TAHUN = "2";
	public static final String TIPE_KOMPONEN_BULAN = "3";
	public static final String TIPE_KOMPONEN_KONSTANTA = "4";
	public static final String TIPE_KOMPONEN_COUNTER = "5";
	public static final String TIPE_KOMPONEN_BULAN_ROMAWI = "6";
	
	// jenis transaksi
	public static final String JENIS_TRX_INVOICE_MANUAL = "INV001";
	public static final String JENIS_TRX_LIVE = "INV003";
	public static final String JENIS_TRX_BERHENTI = "INV004";
	public static final String JENIS_TRX_INVOICE_OTOMATIS = "INV005";
	public static final String JENIS_TRX_HOLD = "INV006";
	public static final String JENIS_TRX_UNHOLD = "INV007";
	public static final String JENIS_TRX_IMPLEMENTASI_TRAINING = "INV008";
	public static final String JENIS_TRX_INVOICE_NOTICE = "INV009";
	public static final String JENIS_TRX_TAGIHAN_TERJADWAL = "INV010";
	public static final String JENIS_TRX_TERIMA_BAYAR = "INV011";
	
	// tipe baris di setting laporan
	public static final String TIPE_BARIS_DATA = "Data";
	public static final String TIPE_BARIS_TEXT = "Text";
	public static final String TIPE_BARIS_PENJUMLAHAN = "Penjumlahan";

	// tipe pembukuan
	public static final String PRS_KECIL = "1";
	public static final String PRS_SEDANG = "2";

	// kode komponen nomor otomatis
	public static final String NOOTO_TRANSAKSI = "1";
	public static final String NOOTO_TAHUN = "2";
	public static final String NOOTO_BULAN = "3";
	public static final String NOOTO_KONSTANTA = "4";
	public static final String NOOTO_COUNTER = "5";

	// tipe combo constant
	public static final String CC_JENIS_LAP = "JNSLAP";
	public static final String CC_SETTING_RPT = "AMBDAT";
	public static final String CC_NOOTO = "JNSNOOT";

	// id default penomoran otomatis di AM59
	public static final String ID_DEFAULT_PENOMORAN_OTOMATIS = "10000";
	
	// Reset nomor otomatis
	public static final String RESET_NO_TAHUNAN = "T";
	public static final String RESET_NO_BULANAN = "B";
	
	// untuk pembanding bagi equality bilangan double
	public static final double EPSILON = 0.000000001d;
	
	// Periode jadwal perulangan jurnal
	public static final String RECU_MINGGU = "M";
	public static final String RECU_BULAN = "B";
	public static final String RECU_TAHUN = "T";
	
	// Asal transaksi jurnal
	public static final String ASAL_TRX_JURNAL_TAMBAH = "N";
	public static final String ASAL_TRX_JURNAL_TEMPLATE = "T";
	public static final String ASAL_TRX_JURNAL_PERULANGAN = "R";
	public static final String ASAL_TRX_JURNAL_NON_GL = "I";
	
	// jumlah hari dalam seminggu
	public static final int JUMLAH_HARI_SEMINGGU = 7;
	
	// terkait upload / download file xlsx
	public static final String REQUIRED_SYMBOL_FOR_EXCEL_TEMPLATE = "*";
	public static final String TRACE = "trace";
	
	// jenis tarif
	public static final String JNSTRF_SKEMA = "SKEMA";
	public static final String JNSTRF_GRUP_DISKON = "GRPDIS";
	public static final String JNSTRF_PERSEN_DISKON = "PCTDIS";
	public static final String JNSTRF_NILAI_DISKON = "NILDIS";
	public static final String JNSTRF_FREE_MONTH = "FREEM";
	
	// status invoice
	public static final String STATUS_INV_BAYAR = "PAID";
	public static final String STATUS_INV_KIRIM = "SENT";
	public static final String STATUS_INV_BELUM_KIRIM = "NOSENT";
	
	// zonaisasi jatuh tempo
	public static final Integer JTH_TEMPO_ZONA_MERAH = 1;
	public static final Integer JTH_TEMPO_ZONA_ORANGE = 2;
	public static final Integer JTH_TEMPO_ZONA_KUNING = 3;
	public static final Integer JTH_TEMPO_ZONA_HIJAU = 4;
	
	// batas akhir masing-masing zonaisasi jatuh tempo
	public static final Integer END_ZONA_MERAH  = 2;
	public static final Integer END_ZONA_ORANGE = 5;
	public static final Integer END_ZONA_KUNING = 8;
	
}
