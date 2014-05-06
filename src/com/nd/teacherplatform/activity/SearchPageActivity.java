package com.nd.teacherplatform.activity;

import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nd.pad.onlinevideo.R;
import com.nd.teacherplatform.constant.Constants;
import com.nd.teacherplatform.interfaces.BackHotWordListInterface;
import com.nd.teacherplatform.interfaces.BackSearchCountInterface;
import com.nd.teacherplatform.interfaces.BackSearchListInterface;
import com.nd.teacherplatform.net.GetHotWordList;
import com.nd.teacherplatform.net.GetSearchCount;
import com.nd.teacherplatform.net.GetSearchList;
import com.nd.teacherplatform.vo.SearchCountVo;
import com.nd.teacherplatform.vo.SearchHistoryVo;
import com.nd.teacherplatform.vo.SearchListVo;
import com.nd.teacherplatform.vo.adapter.SearchKeywordListAdapter;
import com.nd.teacherplatform.vo.adapter.SearchKeywordListAdapter.videoSearchItemClickListener;
import com.nd.teacherplatform.vo.adapter.SearchVideoListAdapter;
import com.nd.teacherplatform.vo.adapter.SearchVideoListAdapter.videoListItemClickListener;
import com.nd.teacherplatform.vo.sqlite.HandlerSearchHistoryTable;
import com.nd.teacherplatform.widget.CustomTextWatcher;

/**
 * 搜索页面
 * 
 * @author shj
 * 
 */
public class SearchPageActivity extends BaseActivity {
	private ArrayList<String> searchHistoryList; // 搜索历史列表
	private Drawable mIconClear; // 搜索文本框清除文本内容图标
	private EditText et_video_search_text;
	private GridView gv_video_search_hot, gv_video_search_history,
			gv_video_search_result;
	private LinearLayout layout_video_search_key, layout_video_search_result;
	private TextView tv_video_searchresut_all, tv_video_searchresut_videoset,
			tv_video_searchresut_video, tv_video_searchresut_teacher,
			tv_video_searchresut_intro;
	private String keyWord = ""; // 搜索关键词

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_video_page);
		mIconClear = this.getResources()
				.getDrawable(R.drawable.ic_search_clear);
		initView();

		// 热门搜索列表
		GetHotWordList.getHotWordList(new BackHotWordListClass());
		// 搜索历史列表
		initSearchHistory();
	}

	private void initView() {
		// TODO initView
		ImageButton btn_search_back = (ImageButton) findViewById(R.id.btn_search_back);
		btn_search_back.setOnClickListener(btnSearchBackClick);

		Button btn_video_search = (Button) findViewById(R.id.btn_video_search);
		btn_video_search.setOnClickListener(btnSearchClick);

		TextView tv_video_search_clear = (TextView) findViewById(R.id.tv_video_search_clear);
		tv_video_search_clear.setOnClickListener(clearSearchHistorykClick);

		layout_video_search_key = (LinearLayout) findViewById(R.id.layout_video_search_key);
		layout_video_search_key.setVisibility(View.VISIBLE);
		layout_video_search_result = (LinearLayout) findViewById(R.id.layout_video_search_result);
		layout_video_search_result.setVisibility(View.GONE);

		et_video_search_text = (EditText) findViewById(R.id.et_video_search_text);
		CustomTextWatcher cusText = new CustomTextWatcher(this,
				et_video_search_text, R.drawable.ic_search_clear);
		et_video_search_text.addTextChangedListener(tbxSearch_TextChanged);
		et_video_search_text.setOnTouchListener(cusText.txtSearch_OnTouch);

		gv_video_search_hot = (GridView) findViewById(R.id.gv_video_search_hot);
		gv_video_search_history = (GridView) findViewById(R.id.gv_video_search_history);
		gv_video_search_result = (GridView) findViewById(R.id.gv_video_search_result);

		tv_video_searchresut_all = (TextView) findViewById(R.id.tv_video_searchresut_all);
		tv_video_searchresut_all.setOnClickListener(searchResultTitleClick);
		tv_video_searchresut_videoset = (TextView) findViewById(R.id.tv_video_searchresut_videoset);
		tv_video_searchresut_videoset
				.setOnClickListener(searchResultTitleClick);
		tv_video_searchresut_video = (TextView) findViewById(R.id.tv_video_searchresut_video);
		tv_video_searchresut_video.setOnClickListener(searchResultTitleClick);
		tv_video_searchresut_teacher = (TextView) findViewById(R.id.tv_video_searchresut_teacher);
		tv_video_searchresut_teacher.setOnClickListener(searchResultTitleClick);
		tv_video_searchresut_intro = (TextView) findViewById(R.id.tv_video_searchresut_intro);
		tv_video_searchresut_intro.setOnClickListener(searchResultTitleClick);

		TextView tv_video_searchresut_allname = (TextView) findViewById(R.id.tv_video_searchresut_allname);
		tv_video_searchresut_allname.setOnClickListener(searchResultTitleClick);
		TextView tv_video_searchresut_videosetname = (TextView) findViewById(R.id.tv_video_searchresut_videosetname);
		tv_video_searchresut_videosetname
				.setOnClickListener(searchResultTitleClick);
		TextView tv_video_searchresut_videoname = (TextView) findViewById(R.id.tv_video_searchresut_videoname);
		tv_video_searchresut_videoname
				.setOnClickListener(searchResultTitleClick);
		TextView tv_video_searchresut_teachername = (TextView) findViewById(R.id.tv_video_searchresut_teachername);
		tv_video_searchresut_teachername
				.setOnClickListener(searchResultTitleClick);
		TextView tv_video_searchresut_introname = (TextView) findViewById(R.id.tv_video_searchresut_introname);
		tv_video_searchresut_introname
				.setOnClickListener(searchResultTitleClick);

		TextView tv_video_searchresut_order_default = (TextView) findViewById(R.id.tv_video_searchresut_order_default);
		tv_video_searchresut_order_default.setTag(0);
		tv_video_searchresut_order_default
				.setOnClickListener(searchResultOrderClick);
		TextView tv_video_searchresut_order_favorable = (TextView) findViewById(R.id.tv_video_searchresut_order_favorable);
		tv_video_searchresut_order_favorable.setTag(1);
		tv_video_searchresut_order_favorable
				.setOnClickListener(searchResultOrderClick);
	}

	// 返回
	private Button.OnClickListener btnSearchBackClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			finish();
		}
	};

	// 搜索
	private Button.OnClickListener btnSearchClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			BtnSearchClick_fun();
		}
	};

	private void BtnSearchClick_fun() {
		keyWord = et_video_search_text.getText().toString().trim();
		SearchHistoryVo searchHistoryVo = new SearchHistoryVo();
		searchHistoryVo.setUserID(-1);
		searchHistoryVo.setKeyword(keyWord);
		if (HandlerSearchHistoryTable.AddSearchHistoryVo(searchHistoryVo)) {
			// 更新搜索历史列表
			searchHistoryList.remove(keyWord);
			searchHistoryList.add(0, keyWord);
			((SearchKeywordListAdapter) gv_video_search_history.getAdapter())
					.notifyDataSetChanged();
		}

		GetSearchCount.getSearchCount(keyWord, new BackSearchCountClass());
	}

	// 搜索回调
	private class BackSearchCountClass implements BackSearchCountInterface {
		@Override
		public void setSearchCountVo(ArrayList<SearchCountVo> vos) {
			layout_video_search_result.setVisibility(View.VISIBLE);
			layout_video_search_key.setVisibility(View.GONE);

			for (int i = 0; i < vos.size(); i++) {
				SearchCountVo sc = vos.get(i);
				switch (sc.tab) {
				case 0:
					tv_video_searchresut_all.setTag(sc.tab);
					tv_video_searchresut_all.setText(sc.count + "");
					break;
				case 1:
					tv_video_searchresut_videoset.setTag(sc.tab);
					tv_video_searchresut_videoset.setText(sc.count + "");
					break;
				case 2:
					tv_video_searchresut_video.setTag(sc.tab);
					tv_video_searchresut_video.setText(sc.count + "");
					break;
				case 3:
					tv_video_searchresut_teacher.setTag(sc.tab);
					tv_video_searchresut_teacher.setText(sc.count + "");
					break;
				case 4:
					tv_video_searchresut_intro.setTag(sc.tab);
					tv_video_searchresut_intro.setText(sc.count + "");
					break;
				default:
					break;
				}
			}
		}
	}

	// 清空搜索历史
	private TextView.OnClickListener clearSearchHistorykClick = new TextView.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (searchHistoryList.size() == 0)
				return;

			AlertDialog alertDialog = new AlertDialog.Builder(
					SearchPageActivity.this)
					.setMessage("确定清空历史记录？")
					.setTitle("提示")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									if (HandlerSearchHistoryTable
											.clearSearchHistory()) {
										searchHistoryList.clear();
										((SearchKeywordListAdapter) gv_video_search_history
												.getAdapter())
												.notifyDataSetChanged();
									}
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							}).create();
			alertDialog.show();
		}
	};

	// 请求热门搜索回调
	private class BackHotWordListClass implements BackHotWordListInterface {
		@Override
		public void setHotWordList(ArrayList<String> nameStrings) {
			SearchKeywordListAdapter searchKeyword = new SearchKeywordListAdapter(
					nameStrings, SearchPageActivity.this);
			searchKeyword
					.setAdapterItemClickListener(new videoSearchItemClickClass());
			gv_video_search_hot.setAdapter(searchKeyword);
		}
	}

	// TODO 搜索关键词列表项点击回调
	private class videoSearchItemClickClass implements
			videoSearchItemClickListener {
		@Override
		public void onClick(String keyWord) {
			et_video_search_text.setText(keyWord);
			BtnSearchClick_fun();
		}
	}

	// 初始化搜索历史列表
	private void initSearchHistory() {
		ArrayList<SearchHistoryVo> searchHistoryVos = HandlerSearchHistoryTable
				.getSearchHistoryVos(new Date(), 10, -1, "");

		searchHistoryList = new ArrayList<String>();
		for (int i = 0; i < searchHistoryVos.size(); i++) {
			searchHistoryList.add(searchHistoryVos.get(i).getKeyword());
		}

		SearchKeywordListAdapter searchKeyword = new SearchKeywordListAdapter(
				searchHistoryList, SearchPageActivity.this);
		searchKeyword
				.setAdapterItemClickListener(new videoSearchItemClickClass());
		gv_video_search_history.setAdapter(searchKeyword);
	}

	// TODO 动态搜索
	public TextWatcher tbxSearch_TextChanged = new TextWatcher() {
		// 缓存上一次文本框内是否为空
		private boolean isnull = true;

		@Override
		public void afterTextChanged(Editable s) {
			if (TextUtils.isEmpty(s)) {
				if (!isnull) {
					et_video_search_text
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, null, null);
					layout_video_search_result.setVisibility(View.GONE);
					layout_video_search_key.setVisibility(View.VISIBLE);
					isnull = true;
				}
			} else {
				if (isnull) {
					et_video_search_text
							.setCompoundDrawablesWithIntrinsicBounds(null,
									null, mIconClear, null);
					isnull = false;
				}
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		/**
		 * 随着文本框内容改变动态改变列表内容
		 */
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}
	};

	// 搜索结果排序：0--默认 1--好评度
	private TextView.OnClickListener searchResultOrderClick = new TextView.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getTag().equals(0)) {

			} else if (v.getTag().equals(1)) {

			}
		}
	};

	// 标题栏切换：0--全部 1--视频集 2--视频 3--讲师 4--课程简介
	private TextView.OnClickListener searchResultTitleClick = new TextView.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (keyWord.isEmpty() || v.getTag() == null)
				return;

			int tab = (Integer) (v.getTag());
			GetSearchList.getSearchList(keyWord, tab,
					Constants.SEARCHVIDEO_ORDER_DEFAULT, 0,
					Constants.SEARCHVIDEO_PAGESIZE, new BackSearchListClass());

		}
	};

	// 搜索结果分页切换回调
	private class BackSearchListClass implements BackSearchListInterface {
		@Override
		public void setSearchListVos(ArrayList<SearchListVo> slvos) {
			SearchVideoListAdapter videoListAdapter = new SearchVideoListAdapter(
					slvos, SearchPageActivity.this);
			videoListAdapter.setListItemClickListener(videoListItemClick);
			gv_video_search_result.setAdapter(videoListAdapter);
		}
	}

	// TODO 视频列表项点击回调
	private videoListItemClickListener videoListItemClick = new videoListItemClickListener() {
		public void onClick(int time) {
			;
		}
	};

}
