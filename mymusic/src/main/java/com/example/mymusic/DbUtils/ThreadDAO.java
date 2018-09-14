package com.example.mymusic.DbUtils;

import com.example.mymusic.FIleinfo.Threadinfo;

import java.util.List;

/**
 * Created by Administrator on 2017-08-15 0015.
 * <p/>
 * 数据访问接口
 */
public interface ThreadDAO {

    /**
     * 添加线程信息
     *
     * @param threadinfo
     */
    public void inisertThread(Threadinfo threadinfo);

    /**
     * 删除线程
     *
     * @param url
     * @param thread_id
     */
    public void deleteThread(String url, int thread_id);

    /**
     * 更新线程下载进度
     *
     * @param url
     * @param thread_id
     * @param finished
     */
    public void updateThread(String url, int thread_id, int finished);

    /**
     * 查询文件的线程信息
     *
     * @param url
     * @return
     */
    public List<Threadinfo> getThreads(String url);

    /**
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url, int thread_id);


}
