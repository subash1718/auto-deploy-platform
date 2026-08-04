import { useState } from "react";
import { analyzeProject, analyzeZipProject, getDownloadUrl } from "../services/analysisService";

export default function HomePage() {
  const [sourceType, setSourceType] = useState("github"); // "github" or "zip"
  const [githubUrl, setGithubUrl] = useState("");
  const [zipFile, setZipFile] = useState(null);
  const [environment, setEnvironment] = useState("production");
  const [expectedUsers, setExpectedUsers] = useState(500);
  const [highAvailability, setHighAvailability] = useState(true);

  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [errorMsg, setErrorMsg] = useState("");

  const handleAnalyze = async () => {
    setErrorMsg("");
    setLoading(true);

    try {
      let response;
      if (sourceType === "github") {
        if (!githubUrl.trim()) {
          alert("Please enter a valid GitHub repository URL.");
          setLoading(false);
          return;
        }
        response = await analyzeProject({
          githubUrl: githubUrl.trim(),
          environment,
          expectedUsers,
          highAvailability,
        });
      } else {
        if (!zipFile) {
          alert("Please select a ZIP file to upload.");
          setLoading(false);
          return;
        }
        const formData = new FormData();
        formData.append("file", zipFile);
        formData.append("environment", environment);
        formData.append("expectedUsers", expectedUsers);
        formData.append("highAvailability", highAvailability);

        response = await analyzeZipProject(formData);
      }

      setResult(response);
    } catch (error) {
      console.error("Analysis Error:", error);
      if (error.response && error.response.data) {
        setErrorMsg(error.response.data.message || "Failed to process project analysis.");
      } else {
        setErrorMsg("Unable to connect to backend service at http://localhost:8080.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="dashboard-container">
      {/* Header */}
      <header className="dashboard-header">
        <h1 className="dashboard-title">⚡ Intelligent Cloud Deployment Platform</h1>
        <p className="dashboard-subtitle">
          Automated multi-cloud infrastructure recommendations, artifact generation & containerization
        </p>
      </header>

      {/* Input Form Card */}
      <div className="form-card">
        {/* Source Toggle Tabs */}
        <div className="tab-group">
          <button
            className={`tab-button ${sourceType === "github" ? "active" : ""}`}
            onClick={() => setSourceType("github")}
          >
            🐙 GitHub Repository
          </button>
          <button
            className={`tab-button ${sourceType === "zip" ? "active" : ""}`}
            onClick={() => setSourceType("zip")}
          >
            📦 Upload ZIP Project
          </button>
        </div>

        {/* Dynamic Source Input */}
        {sourceType === "github" ? (
          <div className="input-group">
            <label className="input-label">GitHub Repository URL</label>
            <input
              type="text"
              className="text-input"
              placeholder="https://github.com/username/repository.git"
              value={githubUrl}
              onChange={(e) => setGithubUrl(e.target.value)}
            />
          </div>
        ) : (
          <div className="input-group">
            <label className="input-label">Select Project ZIP Archive</label>
            <input
              type="file"
              accept=".zip"
              className="file-input"
              onChange={(e) => setZipFile(e.target.files[0])}
            />
          </div>
        )}

        {/* Configuration Grid */}
        <div className="form-grid-2">
          <div className="input-group">
            <label className="input-label">Target Environment</label>
            <select
              className="select-input"
              value={environment}
              onChange={(e) => setEnvironment(e.target.value)}
            >
              <option value="production">Production</option>
              <option value="staging">Staging</option>
              <option value="development">Development</option>
            </select>
          </div>

          <div className="input-group">
            <label className="input-label">Expected Concurrent Users</label>
            <input
              type="number"
              className="text-input"
              value={expectedUsers}
              onChange={(e) => setExpectedUsers(Number(e.target.value))}
            />
          </div>
        </div>

        {/* High Availability Checkbox */}
        <label className="checkbox-container">
          <input
            type="checkbox"
            checked={highAvailability}
            onChange={(e) => setHighAvailability(e.target.checked)}
          />
          <span>Enable High Availability (Multi-AZ ALB & Auto Scaling)</span>
        </label>

        {/* Submit Button */}
        <button className="submit-btn" onClick={handleAnalyze} disabled={loading}>
          {loading ? (
            <>
              <div className="spinner"></div>
              <span>Analyzing & Deploying...</span>
            </>
          ) : (
            <span>🚀 Run Analysis & Deployment Pipeline</span>
          )}
        </button>

        {errorMsg && (
          <div style={{ marginTop: "20px", color: "#f43f5e", fontWeight: "600" }}>
            ⚠️ {errorMsg}
          </div>
        )}
      </div>

      {/* Results Dashboard Grid */}
      {result && (
        <main>
          <div className="cards-grid">
            {/* Repository Card */}
            <div className="info-card">
              <div className="card-header">
                <span className="card-icon">📁</span>
                <h3 className="card-title">Repository Summary</h3>
              </div>
              <div className="data-list">
                <div className="data-row">
                  <span className="data-label">Repository:</span>
                  <span className="data-value">{result.repository || "Project"}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Language:</span>
                  <span className="data-value">{result.language || "Java"}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Framework:</span>
                  <span className="data-value">{result.framework || "Spring Boot"}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Build Tool:</span>
                  <span className="data-value">{result.buildTool || "Maven"}</span>
                </div>
              </div>
            </div>

            {/* Technology Card */}
            <div className="info-card">
              <div className="card-header">
                <span className="card-icon">💻</span>
                <h3 className="card-title">Technology Stack</h3>
              </div>
              <div className="data-list">
                <div className="data-row">
                  <span className="data-label">Language:</span>
                  <span className="badge badge-info">{result.language}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Framework:</span>
                  <span className="badge badge-info">{result.framework}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Build Tool:</span>
                  <span className="badge badge-info">{result.buildTool}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Pipeline Status:</span>
                  <span className="badge badge-success">{result.status || "SUCCESS"}</span>
                </div>
              </div>
            </div>

            {/* Infrastructure Card */}
            <div className="info-card">
              <div className="card-header">
                <span className="card-icon">☁️</span>
                <h3 className="card-title">Infrastructure Recommendation</h3>
              </div>
              <div className="data-list">
                <div className="data-row">
                  <span className="data-label">Cloud Provider:</span>
                  <span className="data-value">{result.cloudProvider}</span>
                </div>
                <div className="data-row">
                  <span className="data-label">Compute Service:</span>
                  <span className="data-value">{result.computeService} ({result.instanceType})</span>
                </div>
                <div className="data-row">
                  <span className="data-label">vCPU / RAM / Storage:</span>
                  <span className="data-value">
                    {result.cpu} vCPU / {result.memory || result.memoryGb} GB / {result.storage || result.storageGb} GB
                  </span>
                </div>
                <div className="data-row">
                  <span className="data-label">Load Balancer:</span>
                  <span className={`badge ${result.loadBalancer ? "badge-success" : "badge-warning"}`}>
                    {result.loadBalancer ? "Enabled" : "Disabled"}
                  </span>
                </div>
                <div className="data-row">
                  <span className="data-label">Auto Scaling:</span>
                  <span className={`badge ${result.autoScaling ? "badge-success" : "badge-warning"}`}>
                    {result.autoScaling ? "Enabled" : "Disabled"}
                  </span>
                </div>
              </div>
            </div>

            {/* Deployment Status Card */}
            <div className="info-card">
              <div className="card-header">
                <span className="card-icon">⚙️</span>
                <h3 className="card-title">Deployment Status</h3>
              </div>
              <div className="data-list">
                <div className="data-row">
                  <span className="data-label">Dockerfile Generated:</span>
                  <span className="badge badge-success">
                    {result.dockerGenerated ? "Generated" : "Existing"}
                  </span>
                </div>
                <div className="data-row">
                  <span className="data-label">Jenkinsfile Generated:</span>
                  <span className="badge badge-success">
                    {result.jenkinsGenerated ? "Generated" : "Existing"}
                  </span>
                </div>
                <div className="data-row">
                  <span className="data-label">Terraform Generated:</span>
                  <span className="badge badge-success">
                    {result.terraformGenerated ? "Generated" : "Existing"}
                  </span>
                </div>
                <div className="data-row">
                  <span className="data-label">Docker Image Built:</span>
                  <span className={`badge ${result.dockerImageBuilt ? "badge-success" : "badge-warning"}`}>
                    {result.dockerImageBuilt ? "Completed" : "Skipped"}
                  </span>
                </div>
              </div>
            </div>
          </div>

          {/* Generated Files & Download Buttons Section */}
          <section className="download-section">
            <h3 style={{ color: "#fff", fontSize: "1.3rem", fontWeight: "600", marginBottom: "8px" }}>
              📥 Download Generated Artifacts
            </h3>
            <p style={{ color: "var(--text-muted)", fontSize: "0.95rem" }}>
              Click any button below to download the generated deployment files for repository:{" "}
              <strong>{result.repository}</strong>
            </p>

            <div className="download-grid">
              <a
                href={getDownloadUrl("dockerfile", result.repository)}
                download
                className="download-btn"
              >
                🐳 Download Dockerfile
              </a>
              <a
                href={getDownloadUrl("jenkinsfile", result.repository)}
                download
                className="download-btn"
              >
                🏗️ Download Jenkinsfile
              </a>
              <a
                href={getDownloadUrl("terraform", result.repository)}
                download
                className="download-btn"
              >
                🌐 Download Terraform (ZIP)
              </a>
              <a
                href={getDownloadUrl("report", result.repository)}
                download
                className="download-btn"
              >
                📄 Download Report (.txt)
              </a>
            </div>
          </section>
        </main>
      )}
    </div>
  );
}
