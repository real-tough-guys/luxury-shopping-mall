<template>
  <v-main class="main">
    <v-container class="privacy-wrap">
      <span style="font-size: 50px" class="mx-10">My Page</span>
      <v-container class="information-wrap">
        <v-avatar size="150">
          <v-img src="https://randomuser.me/api/portraits/men/78.jpg" />
        </v-avatar>
        <div class="privacy">
          <span class="user-name">{{ this.$store.state.users.nickname }}</span>
          <br />
          <router-link to="/editUser" style="color:white;" v-if="isLogin">
            <button>
              회원정보 변경
            </button>
          </router-link>
          <span class="hidden-xs-only">&nbsp;/&nbsp;</span>
          <button @click="logout">로그아웃</button>
          <br />
          <span>가입일: 2020.06.11</span>
        </div>
      </v-container>
    </v-container>
    <v-container class="order-status-wrap">
      <span style="font-size: 30px">주문내역 조회</span>
      <v-container>
        <v-card v-for="(item, index) in myOrder" :key="myOrder.id">
          <v-card-title>{{ index + 1 }}번째 주문</v-card-title>
          <v-card v-for="list in item.carts">
            <v-layout>
              <v-flex xs3>
                <v-img
                  v-bind:src="
                    list.product.productImageurl[0] | loadImgOrPlaceholder
                  "
                  contain
                  height="125px"
                ></v-img>
              </v-flex>
              <v-layout column>
                <v-card-title
                  ><h4>{{ list.product.productName }}</h4></v-card-title
                >
                <v-card-subtitle
                  >색상 : {{ list.color }} || 사이즈 :
                  {{ list.size }}</v-card-subtitle
                >

                <v-card-text>{{
                  `가격 : ${list.product.productPrice} 원 ` | moneyFilter
                }}</v-card-text>
              </v-layout>
            </v-layout>
          </v-card>
          <v-card>
            <v-card-title>배송지 : {{ item.myAddress }}</v-card-title>
            <v-card-title
              >배송 주문 사항 : {{ item.deliveryMessage }}</v-card-title
            >
          </v-card>
          <v-card-subtitle>
            <h3 align="center">
              수량
              <p style="color: orange">{{ item.carts.length }}</p>
              Total Price($ {{ item.totalMoney | moneyFilter }} 원)
            </h3>
          </v-card-subtitle>
        </v-card>
        <v-spacer />
      </v-container>
    </v-container>
  </v-main>
</template>
<script>
import { mapActions } from "vuex";
import myMixin from "@/filter";
import axios from "axios";

export default {
  mixins: [myMixin],
  data() {
    return {
      myOrder: [],
      orderIdCart: []
    };
  },
  created() {
    this.getUserDetails();
  },
  mounted() {
    this.orderStatements();
  },
  methods: {
    ...mapActions({ logout: "users/logout" }),
    ...mapActions({ getMyDetail: "users/details" }),
    ...mapActions({ getMyCart: "carts/getMyCarts" }),
    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({ name: "Main" });
      }
    },
    async getUserDetails() {
      if (!(await this.getMyDetail(this.$store.state.users.id))) {
        await this.$router.push({ name: "Main" });
      }
    },
    async getMyCartList() {
      await this.getMyCart(this.$store.state.users.id);
    },
    orderStatements() {
      axios
        .get("/api/orders", {
          params: { userId: this.$store.state.users.id }
        })
        .then(res => {
          console.log(res.data);
          this.myOrder = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  computed: {
    isLogin() {
      return (
        this.$store.state.users.jwt != undefined ||
        this.$store.state.users.jwt == ""
      );
    }
  }
};
</script>
<style scoped>
.main {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
}

.privacy-wrap {
  max-width: 100%;
  background-color: black;
  color: white;
}

.information-wrap {
  display: flex;
  flex-direction: row;
  margin-top: 15px;
}

.privacy {
  margin-left: 20px;
  font-size: 20px;
}

.user-name {
  font-size: 3rem;
  letter-spacing: 7px;
}

.order-status-wrap {
  max-width: 100%;
}
</style>
