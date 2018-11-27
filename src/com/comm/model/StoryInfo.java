package com.comm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="story_info")
public class StoryInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // 书编号 book_id
    private String bookId = "";
    // 书名  book_title
    private String bookTitle = "";
    // 封面url   cover_url
    private String coverUrl = "";
    // 滚动条用图片url   cover_banner_url
    private String coverBannerUrl = "";
    // 作者  book_author
    private String bookAuthor = "";
    // 简介  book_desc
    private String bookDesc = "";
    // 总字数 word_count
    private int wordCount = 0;
    // 文件保存总目录 book_save_dir
    private String bookSaveDir = "";
    // 适合阅读的性别 gender
    private String gender = "";
    // 章节总数    chapter_count
    private int chapterCount = 0;
    // 小说章节更新时间    ch_upd_date
    private String chUpdDate = "";
    // 小说状态    book_status
    private String bookStatus = "";
    // 创建时间    cr_date
    private String crDate = "";
    // 更新时间    upd_date
    private String updDate = "";
    // bak1    
    private String bak1 = "";
    // bak2    
    private String bak2 = "";
    // bak3    
    private String bak3 = "";
    // bak4    
    private String bak4 = "";
    // bak5    
    private String bak5 = "";
    
    @Id
    @Column(name="book_id")
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    @Column(name="book_title")
    public String getBookTitle() {
        return bookTitle;
    }
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }
    @Column(name="cover_url")
    public String getCoverUrl() {
        return coverUrl;
    }
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    @Column(name="cover_banner_url")
    public String getCoverBannerUrl() {
        return coverBannerUrl;
    }
    public void setCoverBannerUrl(String coverBannerUrl) {
        this.coverBannerUrl = coverBannerUrl;
    }
    @Column(name="book_author")
    public String getBookAuthor() {
        return bookAuthor;
    }
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
    @Column(name="book_desc")
    public String getBookDesc() {
        return bookDesc;
    }
    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }
    @Column(name="word_count")
    public int getWordCount() {
        return wordCount;
    }
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }
    @Column(name="book_save_dir")
    public String getBookSaveDir() {
        return bookSaveDir;
    }
    public void setBookSaveDir(String bookSaveDir) {
        this.bookSaveDir = bookSaveDir;
    }
    @Column(name="gender")
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    @Column(name="chapter_count")
    public int getChapterCount() {
        return chapterCount;
    }
    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }
    @Column(name="ch_upd_date")
    public String getChUpdDate() {
        return chUpdDate;
    }
    public void setChUpdDate(String chUpdDate) {
        this.chUpdDate = chUpdDate;
    }
    @Column(name="book_status")
    public String getBookStatus() {
        return bookStatus;
    }
    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }
    @Column(name="cr_date")
    public String getCrDate() {
        return crDate;
    }
    public void setCrDate(String crDate) {
        this.crDate = crDate;
    }
    @Column(name="upd_date")
    public String getUpdDate() {
        return updDate;
    }
    public void setUpdDate(String updDate) {
        this.updDate = updDate;
    }
    public String getBak1() {
        return bak1;
    }
    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }
    public String getBak2() {
        return bak2;
    }
    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }
    public String getBak3() {
        return bak3;
    }
    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }
    public String getBak4() {
        return bak4;
    }
    public void setBak4(String bak4) {
        this.bak4 = bak4;
    }
    public String getBak5() {
        return bak5;
    }
    public void setBak5(String bak5) {
        this.bak5 = bak5;
    }


}
