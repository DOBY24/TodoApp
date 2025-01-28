<template>
  <div id="container">
    <h2 class="text-center mt-5">ToDo APP</h2>

    <div class="form">
      <input v-bind:placeholder="placeholder" type="text" class="form-control" v-model="currentTodo.description">
      <button @click="isEditing ? updateTodo() : createTodo()" type="button" :class="isEditing ? 'btn btn-warning' : 'btn btn-success'">{{ isEditing ? 'Upravit' : 'Přidat' }}</button>
    </div>
    <table class="table" v-if="todos.length">
      <thead>
      <tr>
        <th width="81%" scope="col">Popis</th>
        <th scope="col">Akce</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="todo in todos" :key="todo.id">
        <td>{{ todo.description }}</td>
        <td>
          <button class="btn btn-primary btn-sm" @click="editTodo(todo.id)">Upravit</button>
          <button class="btn btn-danger btn-sm" @click="deleteTodo(todo.id)">Smazat</button>
        </td>
      </tr>
      </tbody>
    </table>
    <p class="form" v-else>Žádné úkoly nejsou k dispozici.</p>

  </div>
</template>

<script>
import axios from "axios";
import 'bootstrap/dist/css/bootstrap.min.css'

export default {
  name:"ToDo",
  data() {
    return {
      placeholder:"Zadejte popis úkolu",
      todos: [],
      currentTodo: { id: null, description: "" },
      isEditing: false,
      inputText:"",
      apiUrl: "http://localhost:8080/api/todos",
    };
  },
  methods: {
    async fetchTodos() {
      try {
        console.log("Fetching todos...");
        const response = await axios.get(this.apiUrl);
        console.log("Response:", response.data);
        this.todos = response.data;
      } catch (error) {
        console.error("Error while fetching todos:", error.message);
      }
    },
    async createTodo() {
      try {
        if(!this.currentTodo.description.localeCompare("")){
          alert("Nelze vytvořit prázdné ToDo");
          throw Error("Empty description");
        }
        const response = await axios.post(this.apiUrl, {
          description: this.currentTodo.description,
        });
        console.log("Created todo:", response.data);
        this.todos.push(response.data);
        this.clearForm();
      } catch (error) {
        console.error("Error while creating todo:", error.message);
      }
    },
    editTodo(id) {
      this.isEditing = true;
      this.currentTodo.id = id;
      this.currentTodo.description=this.todos.find((todo)=>todo.id===id).description;
    },
    async updateTodo() {
      try {
        if(this.currentTodo.description.localeCompare("")) {
          const response = await axios.put(`${this.apiUrl}/${this.currentTodo.id}`, {
            description: this.currentTodo.description,
          });
          console.log("Updated todo:", response.data);
        }else{
          alert("Nelze vytvořit prázdné ToDo");
        }
        await this.fetchTodos();
        this.clearForm();
      } catch (error) {
        console.error("Error while updating todo:", error.message);
      }
    },
    async deleteTodo(id) {
      try {
        console.log("Deleting todo with id:", id);
        await axios.delete(`${this.apiUrl}/${id}`);
        this.todos = this.todos.filter((todo) => todo.id !== id);
      } catch (error) {
        console.error("Error while deleting todo:", error.message);
      }
    },
    clearForm() {
      this.currentTodo = { id: null, description: "" };
      this.isEditing = false;
    },
  },
  created() {
    this.fetchTodos();
  },
};
</script>

<style scoped>
.form{
  width: 40%;
  margin: 0 auto;
  display: flex;
}
.table{
  width: 40%;
  margin: 0 auto;
}
</style>