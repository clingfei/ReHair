package com.example.rehair.model;

public class ShareReturn {
    private boolean flag;
    private int seqid;

    public ShareReturn(boolean flag, int seqid) {
        this.flag = flag;
        this.seqid = seqid;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setSeqid(int seqid) {
        this.seqid = seqid;
    }

    public int getSeqid() {
        return this.seqid;
    }

     public boolean getFlag() {
        return this.flag;
     }
}
