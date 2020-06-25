<template>
    <viewer v-if="content != null" :initialValue="content"/>
</template>

<script>
import { Editor, Viewer } from '@toast-ui/vue-editor'
export default {
    components: {
        Viewer
    },
    data() {
        return {
            content: null
        }
    },

    mounted() {
        this.content = localStorage.getItem("content")
    }
    
}
</script>