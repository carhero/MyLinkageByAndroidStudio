package com.example.adsoft.mylinkage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "MainActivity";
    /** ViewFlipper 컴포넌트 객체 */
    ViewFlipper m_viewFlipper;

    /** ViewFilpper 안에서 터치된 X축의 좌표 */
    private int m_nPreTouchPosY = 0;

    // Button
    Button button_song;
    Button button_main;
    Button button_info;

    // List View Data asset
    ListView listView;
    ArrayList<String> list_array;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        m_viewFlipper.setOnTouchListener(this);

        // ViewFlipper에 서브 레이아웃 추가
        LinearLayout mainCtrlPage = (LinearLayout) View.inflate(this, R.layout.activity_main_control, null);
        LinearLayout infoPage = (LinearLayout) View.inflate(this, R.layout.activity_infomation, null);
        //LinearLayout songListPage = (LinearLayout) View.inflate(this, R.layout.activity_song_list, null);
        RelativeLayout songListPage = (RelativeLayout) View.inflate(this, R.layout.activity_song_list, null);

        m_viewFlipper.addView(songListPage);    // 0
        m_viewFlipper.addView(mainCtrlPage);    // 1
        m_viewFlipper.addView(infoPage);        // 2

        m_viewFlipper.setDisplayedChild(1);

        button_song = (Button) findViewById(R.id.song_button);
        button_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Song Button눌려졌음", Toast.LENGTH_LONG).show();
            }
        });

        button_main = (Button) findViewById(R.id.button_ctrl);
        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Main Ctrl Button눌려졌음", Toast.LENGTH_LONG).show();
            }
        });

        button_info = (Button) findViewById(R.id.button_info);
        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Info Ctrl Button눌려졌음", Toast.LENGTH_LONG).show();
            }
        });

        // Listview array string set
        list_array = new ArrayList<String>();

        list_array.add("11111");
        list_array.add("22222");
        list_array.add("33333");
        list_array.add("안녕하세요");
        list_array.add("반갑습니다.");
        list_array.add("어서오세요");
        list_array.add("11111");
        list_array.add("22222");
        list_array.add("33333");
        list_array.add("안녕하세요");
        list_array.add("반갑습니다.");
        list_array.add("어서오세요");
        list_array.add("11111");
        list_array.add("22222");
        list_array.add("33333");
        list_array.add("안녕하세요");
        list_array.add("반갑습니다.");
        list_array.add("어서오세요");

        // 어댑터 객체 생성
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list_array);

        //리스트뷰 객체 생성 & 어댑터 설정
        listView = (ListView) findViewById(R.id.listView_song);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick:" + position);
            }
        });
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected");
            }
        });

    }

    private void MoveDownView() {
        Log.d(TAG, "MoveDownView");
        int currentPage = m_viewFlipper.getDisplayedChild();
        View view;

        if(currentPage !=  2) {
            m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.disappear_to_right));
            m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_from_left));
            m_viewFlipper.showNext();

            if(currentPage == 1) // main control page
            {

            }
            else if(currentPage == 0) // Info page
            {
                //tempClass = InfomationActivity.class;
            }

            //finish();

  /*          Intent intent = new Intent(this, m_viewFlipper.getChildAt(0).getClass());
            startActivity(intent);*/
        }
        else {
            m_viewFlipper.requestDisallowInterceptTouchEvent(false);
        }
    }

    private void MovewUpView() {
        Log.d(TAG, "MovewUpView");
        if(m_viewFlipper.getDisplayedChild() !=  0) {
            m_viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.disappear_to_left));
            m_viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.appear_from_right));
            m_viewFlipper.showPrevious();

            m_viewFlipper.setFocusable(true);
        }
        else {
            m_viewFlipper.requestDisallowInterceptTouchEvent(false);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            m_nPreTouchPosY = (int) event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int nTouchPosY = (int) event.getY();

            if (nTouchPosY < m_nPreTouchPosY) {
                MovewUpView();
            } else if (nTouchPosY > m_nPreTouchPosY) {
                //MovewPreviousView();
                MoveDownView();
            }

            m_nPreTouchPosY = nTouchPosY;
        }
        //Log.d(TAG, "onTouchEvent-" + "X:" + event.getX() + "Y:" + event.getY() + "indexOfChild:" + m_viewFlipper.getDisplayedChild());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        /*if (event.getAction() == MotionEvent.ACTION_DOWN) {
            m_nPreTouchPosX = (int) event.getX();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int nTouchPosX = (int) event.getX();

            if (nTouchPosX < m_nPreTouchPosX) {
                MoveNextView();
            } else if (nTouchPosX > m_nPreTouchPosX) {
                MovewPreviousView();
            }

            m_nPreTouchPosX = nTouchPosX;
        }
*/
        return false;
    }
}
