<template xmlns:v-contextmenu="http://www.w3.org/1999/xhtml">
  <Menu mode="horizontal" :theme="theme" active-name="1" class="nav">
    <div class="fl dib h-100 logo pa1 mr4">
      <router-link to="/home" class="h-100 dib">
        <Logo class="h-100"></Logo>
      </router-link>
    </div>
    <div class="fl dib">
      <p class="light-blue" style="font-family: 方正清刻本悦宋简体;font-size: 1.2rem">个性化投顾</p>
    </div>
    <div class="fr dib">
      <MenuItem name="0" to="/home" style="padding: 0 2.5rem">
        <Icon type="ios-bonfire" size="20"/>
        首页
      </MenuItem>
      <Submenu name="1">
        <template slot="title">
          <Icon type="md-analytics" size="16"/>
          市场行情
        </template>
        <MenuItem name="1-1" to="/50ETF">50ETF</MenuItem>
        <MenuItem name="1-2" to="/50ETFOption">50ETF期权</MenuItem>
      </Submenu>
      <Submenu name="2">
        <template slot="title">
          <Icon type="ios-construct" size="16"/>
          构建组合
        </template>
        <MenuItem name="2-1" to="/allocation">资产配置</MenuItem>
        <MenuItem name="2-2" to="/hedging">套期保值</MenuItem>
        <MenuItem name="2-3" to="/diy">DIY</MenuItem>
      </Submenu>
      <MenuItem name="3" to="/myPortfolio">
        <Icon type="md-filing" size="16"/>
        我的组合
      </MenuItem>

      <div class="dib pr3 pl6">
        <router-link v-if="isLogin" to="/prompt" class="black">
          <Badge :count="unreadCount" class="di">
            <Icon type="md-notifications-outline" size="30"/>
          </Badge>
        </router-link>
      </div>

      <div class="dib pl3 pr4" v-contextmenu:contextmenu>
        <a v-if="isLogin">
          <!--此头像应为用户自定义头像-->
          <Avatar style="background-color: #87d068" icon="ios-person"/>
        </a>
        <router-link v-if="!isLogin" :to="'/login'">
          <Avatar style="background-color: #87d068" icon="ios-person"/>
        </router-link>
      </div>
    </div>
    <v-contextmenu ref="contextmenu" event-type="click" style="z-index: 10000001">
      <v-contextmenu-item @click="profile">个人信息</v-contextmenu-item>
      <v-contextmenu-item divider></v-contextmenu-item>
      <v-contextmenu-item @click="logout">登出</v-contextmenu-item>
    </v-contextmenu>
  </Menu>
</template>

<script>
  import Logo from "./Logo";

  export default {
    name: "NavBar",
    components: {Logo},
    data() {
      return {
        theme: 'light',
        userName: '',
        isLogin: false,
        unreadCount: 0
      }
    },
    created: function () {
      this.userName = this.$cookie.get("userName");

      this.axios.post("/backend/auth")
        .then((res) => {
          this.isLogin = (res.data.code === 0);
        });

      this.axios.get("/backend/message/count")
        .then((res) => {
          this.unreadCount = res.data.data;
        });
    },
    methods: {
      logout: function () {
        this.axios.post("/backend/user/logout")
          .then((res) => {
            if (res.data.code === 0) {
              this.$Message.success("登出成功");
              this.isLogin = false;
              this.$router.push("/50ETF");
            }
          })
      },
      profile:function () {
        this.$router.push('/profile/'+this.userName);
      }
    }
  }

</script>

<style scoped>
  Logo {
    height: 100%;
  }

  .nav {
    box-shadow: 0 0 16px 1px rgba(0, 0, 0, .05);
  }

  .logo:after {
    content: "";
    display: block;
    width: 1px;
    height: 24px;
    background: #ebedee;
    position: absolute;
    top: 20px;
    left: 273px;
  }

  @font-face {
    font-family: '方正清刻本悦宋简体';
    src: url("../../assets/fonts/navbar/sfont.svg"),
    url("../../assets/fonts/navbar/sfont.eot"),
    url("../../assets/fonts/navbar/sfont.ttf"),
    url("../../assets/fonts/navbar/sfont.woff"),
    url("../../assets/fonts/navbar/sfont.woff2");
  }
</style>
