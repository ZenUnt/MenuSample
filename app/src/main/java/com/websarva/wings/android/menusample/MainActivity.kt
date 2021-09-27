package com.websarva.wings.android.menusample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    // リストビューに表示するリストデータ
    private var _menuList: MutableList<MutableMap<String,Any>> = mutableListOf()
    // SimpleAdapterの第4引数fromに使用するプロパティ
    private val _from = arrayOf("name", "price")
    // SimpleAdapterの第5引数toに使用するプロパティ
    private val _to = intArrayOf(R.id.tvMenuNameRow, R.id.tvMenuPriceRow)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 定食メニューListオブジェクトをprovateメソッドを利用しプロパティに格納
        _menuList = createTeishokuList()
        // 画面部品ListViewを取得
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        // SimpleAdapterを生成
        val adapter = SimpleAdapter(this@MainActivity, _menuList, R.layout.row, _from, _to)
        // アダプタの登録
        lvMenu.adapter = adapter
        // リストタップのリスナクラス登録
        lvMenu.onItemClickListener = ListItemClickListener()

        registerForContextMenu(lvMenu)
    }

    private fun createTeishokuList(): MutableList<MutableMap<String, Any>> {
        // 定食メニューリスト用のListオブジェクトを作成
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
        // から揚げ定食のデータを格納するMapオブジェクトの作成とリストへの登録
        var menu = mutableMapOf<String, Any>("name" to "から揚げ定食", "price" to 800, "desc" to "若鶏の唐揚にサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "ハンバーグ定食", "price" to 850, "desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "焼肉定食", "price" to 900, "desc" to "豚の焼き肉にサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "焼き魚定食", "price" to 800, "desc" to "サバの焼き魚にサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "とんかつ定食", "price" to 850, "desc" to "とんかつにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "生姜焼き定食", "price" to 800, "desc" to "生姜焼きにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "野菜炒め定食", "price" to 700, "desc" to "野菜炒めにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        menu = mutableMapOf<String, Any>("name" to "海老フライ定食", "price" to 900, "desc" to "海老フライにサラダ、ご飯とお味噌汁が付きます。")
        menuList.add(menu)
        return menuList
    }

    // リストがタップされた時の処理が記述されたメンバクラス
    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行のデータを取得
            val item = parent.getItemAtPosition(position) as MutableMap<String, Any>
            // 注文処理
            order(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // オプションメニュー用xmlファイルをインフレイト
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        return true
    }

    private fun createCurryList(): MutableList<MutableMap<String, Any>> {
        // カレーメニューリスト用のListオブジェクト
        val menuList: MutableList<MutableMap<String, Any>> = mutableListOf()
        // カレーメニューのデータ作成とメニューリストへの登録
        var menu = mutableMapOf<String,Any>("name" to "ビーフカレー", "price" to 720, "desc" to "特選スパイスを効かせた国産ビーフ100%のカレーです。")
        menuList.add(menu)
        menu = mutableMapOf<String,Any>("name" to "ポークカレー", "price" to 500, "desc" to "特選スパイスを効かせた国産ポーク100%のカレーです。")
        menuList.add(menu)
        menu = mutableMapOf<String,Any>("name" to "チキンカレー", "price" to 500, "desc" to "特選スパイスを効かせた国産チキン100%のカレーです。")
        menuList.add(menu)
        menu = mutableMapOf<String,Any>("name" to "キーマカレー", "price" to 600, "desc" to "特選スパイスを効かせた国産ひき肉100%のカレーです。")
        menuList.add(menu)
        return menuList
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var returnVal = true
        // 長押しされたビューに関する情報が格納されたオブジェクト
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        // 長押しされたリストのポジション取得
        val listPosition = info.position
        // ポジションから長押しされたメニュー情報Mapオブジェクトを取得
        val menu = _menuList[listPosition]

        // 選択されたメニューのIDのR値による分岐
        when (item.itemId) {
            R.id.menuListContextDesc -> {
                // メニューの説明文字列を取得
                val desc = menu["desc"] as String
                // トーストを表示
                Toast.makeText(this@MainActivity, desc, Toast.LENGTH_LONG).show()
            }
            R.id.menuListContextOrder ->
                order(menu)
            else ->
                // 親クラスの同名メソッドを呼び出し
                returnVal = super.onContextItemSelected(item)
        }
        return returnVal
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var returnVal = true
        // 選択されたメニューのIDのR値による分岐
        when (item.itemId) {
            // 定食メニューが選択された場合
            R.id.menuListOptionTeishoku ->
                _menuList = createTeishokuList()
            // カレーメニューが選択された場合
            R.id.menuListOptionCurry ->
                _menuList = createCurryList()
            else ->
                // 親クラスの同名メソッドを呼び出し、その戻り値をこの関数の戻り値にする
                returnVal = super.onOptionsItemSelected(item)
        }
        // 画面部品ListViewを取得
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        // SimpleAdapterを選択されたメニューデータで作成
        val adapter = SimpleAdapter(this@MainActivity, _menuList, R.layout.row, _from, _to)
        // アダプタの登録
        lvMenu.adapter = adapter
        return returnVal
    }

    override fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenu.ContextMenuInfo) {
        // 親クラスの同名メソッド呼び出し
        super.onCreateContextMenu(menu, view, menuInfo)
        // コンテキストメニュー用のxmlファイルをインフレイト
        menuInflater.inflate(R.menu.menu_context_menu_list, menu)
        // コンテキストメニューのヘッダタイトルを設定
        menu.setHeaderTitle(R.string.menu_list_context_header)
    }

    private fun order(menu: MutableMap<String, Any>) {
        // 定食名を金額を取得(Mapの値がAny型なのでキャストが必要)
        val menuName = menu["name"] as String
        val menuPrice = menu["price"] as Int
        // インデントオブジェクトを生成
        val intent2MenuThanks = Intent(this@MainActivity, MenuThanksActivity::class.java)
        // 第2画面に送るデータを格納
        intent2MenuThanks.putExtra("menuName", menuName)
        intent2MenuThanks.putExtra("menuPrice", "${menuPrice}円")
        // 第2画面の起動
        startActivity(intent2MenuThanks)
    }
}