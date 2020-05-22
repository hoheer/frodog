

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class addschedule extends AppCompatActivity
{
    private TextView mDateText;
    private EditText mTitleText;
    private EditText mContentText;
    private String SeletedDate;
    private int mMemoID;
    private SharedPref sharedPref;

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id==R.id.action_search)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu) ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        sharedPref =new SharedPref(this);

        // 테마
        if (sharedPref.loadNightModeState())
        {
            setTheme(R.style.DarkTheme);
        }
        else
        {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.addschedule);

        //mDateText = (TextView) findViewById(R.id.TextDate);
        mTitleText = (EditText) findViewById(R.id.Title_Edit);
        mContentText = (EditText) findViewById(R.id.Cotent_Edit);


        Intent intent = getIntent();
        if(intent != null)
        {
            SeletedDate = intent.getStringExtra("SelectedDate");
            //mDateText.setText(SeletedDate);

            mMemoID = intent.getIntExtra("id",-1);

            // 받아온 ID가 있으면 테이블 수정으로 간주하여 타이틀과 컨텐츠를 불러온다.
            if(mMemoID!=-1)
            {
                String title = intent.getStringExtra("title");
                String contents = intent.getStringExtra("contents");

                mTitleText.setText(title);
                mContentText.setText(contents);
            }
        }


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();


        String title = mTitleText.getText().toString();
        String contents = mContentText.getText().toString();

        if(TextUtils.isEmpty(title)) // 제목이 비어있는지 판단
        {
            Toast.makeText(this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        // 저장할 데이터를 contentValues에 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.DATE,SeletedDate);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_CONTENTS, contents);

        // helper 인스턴스에서 쓰기가능한 데이터베이스를 가져옴
        SQLiteDatabase db = MemoDBHelper.getInstance(this).getWritableDatabase();


        if (mMemoID == -1)
        {
            long newRowId = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, contentValues);

            if (newRowId == -1)
            {
                Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(this, "메모가 저장되었습니다.", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }

            // 받아온 ID값이 있으므로 데이터를 업데이트(수정) 시킨다.
        } else
        {
            int count = db.update(MemoContract.MemoEntry.TABLE_NAME,contentValues,MemoContract.MemoEntry._ID+"="+mMemoID,null);

            if(count==0)
            {
                Toast.makeText(this,"수정에 문제가 발생하였습니다.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this,"수정 되었습니다.",Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }

        super.onBackPressed();
    }
}
