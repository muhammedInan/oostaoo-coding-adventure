package com.oostaoo.org.oostaoocodingadventure

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.oostaoo.org.oostaoocodingadventure.database.campaign.Campaign
import com.oostaoo.org.oostaoocodingadventure.database.candidat.Candidat
import com.oostaoo.org.oostaoocodingadventure.ui.addCandidat.AddCandidatFragment
import com.oostaoo.org.oostaoocodingadventure.ui.configEmail.ConfigEmailFragment
import com.oostaoo.org.oostaoocodingadventure.ui.listQuestions.ListQuestionsFragment
import com.oostaoo.org.oostaoocodingadventure.ui.myTests.MyTestsFragment
import com.oostaoo.org.oostaoocodingadventure.ui.newTest.NewTestFragment
import com.oostaoo.org.oostaoocodingadventure.ui.testCandidats.TestCandidatsFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeActivity : AppCompatActivity(),
    MyTestsFragment.OnCampaignListFragmentInteractionListener,
    TestCandidatsFragment.OnCandidatListFragmentInteractionListener,
    TestCandidatsFragment.OnAddCandidatListener,
    AddCandidatFragment.OnButtonConfigEmailClickListener,
    ConfigEmailFragment.OnButtonBackCandidatsClickListener,
    NewTestFragment.OnButtonListQuestionsClickListener,
    ListQuestionsFragment.OnPostCampaignClickListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_my_tests, R.id.nav_new_test, R.id.nav_user_profile,
                R.id.nav_business_profile, R.id.nav_facturation, R.id.nav_data_protection, R.id.nav_users), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        bt_deconnection.setOnClickListener {
            val sharedpreferences = getSharedPreferences("sharedpreferences", 0)
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.remove("identifier")
            editor.remove("password")
            editor.remove("email")
            editor.remove("id")
            editor.apply()
            startActivity(Intent(this, ConnectionActivity::class.java))
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {

        navController = findNavController(R.id.nav_host_fragment)
        if(nav_view.tv_username.text == "" && nav_view.tv_email.text == "") {
            val sharedPreferences: SharedPreferences = getSharedPreferences("sharedpreferences", 0)
            nav_view.tv_username.text = sharedPreferences.getString("identifier", "")
            nav_view.tv_email.text = sharedPreferences.getString("email", "")
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCampaignListFragmentInteraction(item: Campaign) {

        val bundle = Bundle()
        bundle.putInt("id", item.id)
        navController.navigate(R.id.nav_test_candidats, bundle)
    }
    override fun onCandidatListFragmentInteraction(item: Candidat) {

        val bundle = Bundle()
        bundle.putInt("id", item.id)
        navController.navigate(R.id.nav_info_candidat, bundle)
    }

    override fun onAddCandidatInteraction(item: Campaign) {
        val bundle = Bundle()
        bundle.putInt("id", item.id)
        navController.navigate(R.id.nav_add_candidat, bundle)
    }

    override fun onButtonConfigEmailClickListener(campaign: Campaign, names: ArrayList<String>, emails: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putInt("id", campaign.id)
        bundle.putStringArrayList("names", names)
        bundle.putStringArrayList("emails", emails)
        navController.navigate(R.id.nav_config_email, bundle)
    }

    override fun onButtonBackCandidatsClickListener() {
        navController.popBackStack()
    }

    override fun onButtonListQuestionsClick(name: String, level: String, langs: String, copy_paste: Boolean,
                                            sent_report: Boolean, profile: Int, user: Int, technologiesId: ArrayList<Int>,
                                            technologiesName: ArrayList<String>) {
        val bundle = Bundle()
        bundle.putString("Name", name)
        bundle.putString("level", level)
        bundle.putString("langs", langs)
        bundle.putBoolean("copy_paste", copy_paste)
        bundle.putBoolean("sent_report", sent_report)
        bundle.putInt("profile", profile)
        bundle.putInt("user", user)
        bundle.putIntegerArrayList("technologiesId", technologiesId)
        bundle.putStringArrayList("technologiesName",technologiesName)
        navController.navigate(R.id.nav_list_questions, bundle)
    }

    override fun onPostCampaignClickListener() {
        navController.navigate(R.id.nav_my_tests)
    }
}
