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
                      :rules="[rules.required]"
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
              <v-row v-if=showIsSaved
                     :style="{color:colorRed}">
                {{ isSaved }}
              </v-row>
            </v-container>
          </v-form>
        </div>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-btn
            :disabled="!email||!(password.length >= 8)||!(passwordCheck.length >= 8)||!(nickname.length >= 2)"
            color="blue-grey darken-3"
            class="mr-3 white--text"
            @click="save"
        >
          Sign up
          <v-icon right>mdi-arrow-right-thick</v-icon>
        </v-btn>

        <v-btn color="blue-grey darken-3" class="mr-4 white--text">
          Cancel
          <v-icon right>mdi-cancel</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import {isDuplicatedEmail} from "@/api";
import {save} from "@/api";

export default {
  data() {
    return {
      show1: false,
      show2: false,
      showIsSaved: false,

      email: "",
      password: "",
      passwordCheck: "",
      nickname: "",
      valid: true,
      isSaved: [],
      colorRed: "red",

      rules: {
        required: input => !!input || "Required.",
        minPw: password => password.length >= 8 || "Min 8 characters",
        minName: nickName => nickName.length >= 2 || "Min 2 characters",
        pwCheck: passwordCheck =>
            this.password === passwordCheck || "Password mismatch"
      }
    };
  },
  methods: {
    isDuplicatedEmail: function () {
      isDuplicatedEmail(this.email)
          .then(res => {
            alert(res.data)
          })
          .catch(res => {
            alert(res.response.data)
          })
    },
    save: function () {
      const userSignupDto = {
        email: this.email,
        password: this.password,
        passwordCheck: this.passwordCheck,
        nickname: this.nickname
      }
      save(userSignupDto)
          .then(res => {
            alert("회원가입 성공!")
            window.open("/login", "_self");
          })
          .catch(res => {
            this.isSaved = res.response.data
            alert(this.isSaved)
          })
    }
  }
};
</script>
<style scoped></style>
