import axios from "axios";

const API = "http://localhost:8080";

export async function analyzeProject(data) {

    const response = await axios.post(
        `${API}/analyze`,
        data
    );

    return response.data;
}
