package com.huahansoft.hhsoftlibrarykit.third;

public class Event {
    public static class ThirdLoginEvent{
        private HHSoftThirdTools.ThirdLoginType loginType;
        private HHSoftThirdLoginInfo loginInfo;

        public ThirdLoginEvent(HHSoftThirdTools.ThirdLoginType loginType, HHSoftThirdLoginInfo loginInfo) {
            this.loginType=loginType;
            this.loginInfo = loginInfo;
        }

        public HHSoftThirdTools.ThirdLoginType getLoginType() {
            return loginType;
        }

        public void setLoginType(HHSoftThirdTools.ThirdLoginType loginType) {
            this.loginType = loginType;
        }

        public HHSoftThirdLoginInfo getLoginInfo() {
            return loginInfo;
        }

        public void setLoginInfo(HHSoftThirdLoginInfo loginInfo) {
            this.loginInfo = loginInfo;
        }
    }
    public static class ThirdShareEvent{
        private int shareType;
        private int shareResult;

        public ThirdShareEvent(int shareType, int shareResult) {
            this.shareType = shareType;
            this.shareResult = shareResult;
        }

        public int getShareType() {
            return shareType;
        }

        public void setShareType(int shareType) {
            this.shareType = shareType;
        }

        public int getShareResult() {
            return shareResult;
        }

        public void setShareResult(int shareResult) {
            this.shareResult = shareResult;
        }
    }
    public static class ThirdPayEvent{
        private int payType;
        private int payResult;

        public ThirdPayEvent(int payType, int payResult) {
            this.payType = payType;
            this.payResult = payResult;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getPayResult() {
            return payResult;
        }

        public void setPayResult(int payResult) {
            this.payResult = payResult;
        }
    }
}
