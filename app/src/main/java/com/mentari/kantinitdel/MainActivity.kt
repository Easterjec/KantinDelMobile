package com.mentari.kantinitdel

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mentari.kantinitdel.activity.LoginActivity
import com.mentari.kantinitdel.database.SharedPreferences
import com.mentari.kantinitdel.fragment.AkunFragment
import com.mentari.kantinitdel.fragment.BerandaFragment
import com.mentari.kantinitdel.fragment.KeranjangFragment

class MainActivity : AppCompatActivity() {

    private val fragmentBeranda: Fragment = BerandaFragment()
    private val fragmentAkun: Fragment = AkunFragment()
    private val fragmentKeranjang: Fragment = KeranjangFragment()

    private val fm: FragmentManager = supportFragmentManager
    private var active: Fragment = fragmentBeranda

    private lateinit var menu: Menu
    private lateinit var menuItem: MenuItem
    private lateinit var bottomNavigationView: BottomNavigationView

    private var statuslogin = false
    private lateinit var s: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        s = SharedPreferences(this)

        setUpBottomNav()
    }

    private fun setUpBottomNav(){
        fm.beginTransaction().add(R.id.container, fragmentBeranda).show(fragmentBeranda).commit()
        fm.beginTransaction().add(R.id.container, fragmentAkun).hide(fragmentAkun).commit()
        fm.beginTransaction().add(R.id.container, fragmentKeranjang).hide(fragmentKeranjang).commit()

        bottomNavigationView = findViewById(R.id.nav_view)
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.beranda -> {
                    callFragment(0, fragmentBeranda)
                }
                R.id.keranjang-> {
                    callFragment(1, fragmentKeranjang)
                }
                R.id.akun -> {
                    if(s.getStatusLogin()){
                        callFragment(2, fragmentAkun)
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }

                }
            }
            false
        }
    }

    private fun callFragment(int: Int, fragment: Fragment){
        Log.d("Respons","Akun")
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }
}

