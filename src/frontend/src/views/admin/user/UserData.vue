<template>
  <div>
    <v-card>
      <v-card-title>
        User Table
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table
          class="elevation-1"
          v-if="UserList"
          v-model="selected"
          show-select
          :headers="headers"
          :items="UserList"
          :single-select="singleSelect"
          :search="search"
          hide-default-footer
      >
        <template slot="items" slot-scope="props">
          <tr>
            <td>{{ props.item.id }}</td>
            <td>{{ props.item.username }}</td>
            <td>{{ props.item.email }}</td>
          </tr>
        </template>
        <template v-slot:top>
          <v-switch
              v-model="singleSelect"
              label="Single select"
              class="pa-3"
          ></v-switch>
        </template>
      </v-data-table>

      <v-btn small @click="deleteItem" class="mx-2" fab dark color="indigo">
        <v-icon dark>
          mdi-minus
        </v-icon>
      </v-btn>

      <v-dialog v-model="UserCreateDialog" width="500">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              small
              v-bind="attrs"
              v-on="on"
              class="mx-2"
              fab
              dark
              color="indigo"
          >
            <v-icon dark>
              mdi-plus
            </v-icon>
          </v-btn>
        </template>

        <v-card>
          <v-card-title class="headline grey lighten-2">
            회원 등록
          </v-card-title>

          <v-card-text>
            <UserCreate/>
          </v-card-text>

          <v-divider></v-divider>

          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="error" text @click="UserCreateDialog = false">
              Cancel
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>
<script>
import UserCreate from "../user/UserCreate.vue";
import {mapActions} from "vuex";

export default {
  components: {
    UserCreate
  },
  data() {
    return {
      UserCreateDialog: false,
      selected: [],
      singleSelect: false,
      search: "",
      headers: [
        {text: "no", value: "id", sortable: true},
        {text: "이름", value: "nickname", sortable: true},
        {text: "이메일", value: "email", sortable: true}
      ],
      UserList: []
    };
  },
  created() {
    this.getUsers();
  },
  methods: {
    ...mapActions({getUserList: "users/findAll"}),
    ...mapActions({delUser: "users/deleteUserWithId"}),
    deleteItem() {
      console.log(this.selected);
      for (var i = 0; i < this.selected.length; i++) {
        var selectId = this.selected[i].id;
        if (selectId === this.$store.state.users.details.id) {
          continue;
        }
        this.delUser(selectId);
        this.getUsers();
        this.UserList.splice(selectId - 1, 1);
      }
    },
    async getUsers() {
      if (!await this.getUserList()) {
        await this.$router.push({name: "Main"});
      }
      ;
      this.UserList = this.$store.state.users.userList;
    }
  }
};
</script>
