import axios from "axios";

const API = "http://localhost:8080";

export async function analyzeProject(data) {
  const response = await axios.post(`${API}/analyze`, data);
  return response.data;
}

export async function analyzeZipProject(formData) {
  const response = await axios.post(`${API}/analyze-zip`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
}

export function getDownloadUrl(type, repository) {
  return `${API}/download/${type}?repository=${encodeURIComponent(repository)}`;
}
