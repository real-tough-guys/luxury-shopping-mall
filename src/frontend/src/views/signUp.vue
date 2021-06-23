<template>
  <div id="signUp">
    <v-card width="400" class="mx-auto mt-5">
      <v-card-title style="background-color: grey; margin-bottom:5px;">
        <h3 style="color: white">
          Sign Up
        </h3>
      </v-card-title>
      <v-card-text>
        <div id="signUpForm">
          <v-form>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="email"
                      :rules="[rules.required, rules.validateEmailType]"
                      label="Email address"
                      prepend-icon="mdi-account-circle"
                      required
                  ></v-text-field>
                </v-col>
                <v-btn
                    :disabled="!email"
                    id="checkEmail"
                    elevation="1"
                    color="blue-grey darken-3"
                    class="mr-3 white--text"
                    @click="isDuplicatedEmail"
                >
                  check
                </v-btn>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="password"
                      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                      :rules="[rules.required, rules.minPw]"
                      :type="show1 ? 'text' : 'password'"
                      name="input-10-1"
                      label="Password"
                      hint="At least 8 characters"
                      prepend-icon="mdi-lock"
                      counter
                      @click:append="show1 = !show1"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="passwordCheck"
                      :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                      :rules="[rules.required, rules.minPw, rules.pwCheck]"
                      :type="show2 ? 'text' : 'password'"
                      name="input-10-1"
                      label="Password Check"
                      hint="Enter your password once more."
                      prepend-icon="mdi-lock"
                      counter
                      @click:append="show2 = !show2"
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-text-field
                      v-model="nickname"
                      :rules="[rules.minName]"
                      label="Nickname"
                      prepend-icon="mdi-account"
                      required
                  ></v-text-field>
                </v-col>
              </v-row>
              <v-row>
                <v-btn  rounded @click="showApi">주소 찾기</v-btn>
                <v-col>
                  <v-text-field
                      name="input-10-1"
                      v-model="zip" label="우편번호"
                      outlined
                  ></v-text-field>
                  <v-text-field
                      name="input-10-1"
                      v-model="nomalAddress" label="기본주소"
                      outlined
                  ></v-text-field>
                  <v-text-field
                      name="input-10-1"
                      v-model="detailAddress" label="상세주소"
                      outlined
                  ></v-text-field>

                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn
            :disabled="
            !email ||
              !(password.length >= 8) ||
              !(passwordCheck.length >= 8) ||
              !(nickname.length >= 2)
          "
            color="blue-grey darken-3"
            class="mr-3 white--text"
            @click="save"
        >
          Sign up
          <v-icon right>mdi-arrow-right-thick</v-icon>
        </v-btn>
        <v-btn
            color="blue-grey darken-3"
            class="mr-4 white--text"
            @click="$router.go(-1)"
        >
          Cancel
          <v-icon right>mdi-cancel</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import { mapActions } from "vuex";
export default {
  data() {
    return {
      show1: false,
      show2: false,
      email: "",
      password: "",
      passwordCheck: "",
      nickname: "",
      rules: {
        required: input => !!input || "Required.",
        validateEmailType: email =>
            /.+@.+\..+/.test(email) || "E-mail must be valid",
        minPw: password =>
            (password.length >= 8 && password.length <= 20) ||
            "Min 8 and Max 20 characters",
        minName: nickName =>
            (nickName.length >= 2 && nickName.length <= 8) ||
            "Min 2 and Max 8 characters",
        pwCheck: passwordCheck =>
            this.password === passwordCheck || "Password mismatch"
      },
      zip: "",
      nomalAddress: "",
      detailAddress: ""
    };
  },
  methods: {
    ...mapActions({ signup: "users/signUp" }),
    ...mapActions({ duplicateEmail: "users/isDuplicatedEmail" }),
    isDuplicatedEmail: function() {
      this.duplicateEmail(this.email);
    },
    async save() {
      const userSignupDto = {
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname,
        address : this.nomalAddress+"-"+ this.detailAddress+"-"+this.zip
      };
      if (await this.signup(userSignupDto)) {
        await this.$router.push({ name: "Login" });
      }
    },
    showApi() {
      new window.daum.Postcode({
        oncomplete: data => {
          let fullRoadAddr = data.roadAddress; // 도로명 주소 변수
          let extraRoadAddr = "";

          if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
            extraRoadAddr += data.bname;
          }
          if (data.buildingName !== "" && data.apartment === "Y") {
            extraRoadAddr +=
                extraRoadAddr !== ""
                    ? ", " + data.buildingName
                    : data.buildingName;
          }
          if (extraRoadAddr !== "") {
            extraRoadAddr = " (" + extraRoadAddr + ")";
          }
          if (fullRoadAddr !== "") {
            fullRoadAddr += extraRoadAddr;
          }
          this.zip = data.zonecode;
          this.nomalAddress = fullRoadAddr;
        }
      }).open();
    }
  }
};
</script>
<style scoped></style>