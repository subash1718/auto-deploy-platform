import { useState, useEffect } from "react";
import {
  analyzeProject,
  analyzeZipProject,
  fetchDeployments,
  getDownloadUrl,
} from "../services/analysisService";

export default function HomePage() {
  const [activeTab, setActiveTab] = useState("deploy"); // "deploy", "analytics", "history"
  const [sourceType, setSourceType] = useState("github"); // "github", "zip"

  const [githubUrl, setGithubUrl] = useState("");
  const [zipFile, setZipFile] = useState(null);
  const [environment, setEnvironment] = useState("production");
  const [expectedUsers, setExpectedUsers] = useState(500);
  const [highAvailability, setHighAvailability] = useState(true);

  const [dockerRegistry, setDockerRegistry] = useState("NONE");
  const [dockerRepository, setDockerRepository] = useState("");
  const [generateKubernetes, setGenerateKubernetes] = useState(true);
  const [generateHelm, setGenerateHelm] = useState(true);
  const [deployToAws, setDeployToAws] = useState(true);

  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState(null);
  const [errorMsg, setErrorMsg] = useState("");

  const [history, setHistory] = useState([]);
  const [selectedHistoryRecord, setSelectedHistoryRecord] = useState(null);

  useEffect(() => {
    if (activeTab === "history") {
      loadHistory();
    }
  }, [activeTab]);

  const loadHistory = async () => {
    try {
      const data = await fetchDeployments();
      setHistory(data);
      if (data && data.length > 0 && !selectedHistoryRecord) {
        setSelectedHistoryRecord(data[0]);
      }
    } catch (err) {
      console.error("Failed to load deployment history:", err);
    }
  };

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
          dockerRegistry,
          dockerRepository: dockerRepository.trim(),
          generateKubernetes,
          generateHelm,
          deployToAws,
        });
      } else {
        if (!zipFile) {
          alert("Please select a ZIP project archive.");
          setLoading(false);
          return;
        }
        const formData = new FormData();
        formData.append("file", zipFile);
        formData.append("environment", environment);
        formData.append("expectedUsers", expectedUsers);
        formData.append("highAvailability", highAvailability);
        formData.append("dockerRegistry", dockerRegistry);
        formData.append("dockerRepository", dockerRepository.trim());
        formData.append("generateKubernetes", generateKubernetes);
        formData.append("generateHelm", generateHelm);
        formData.append("deployToAws", deployToAws);

        response = await analyzeZipProject(formData);
      }

      setResult(response);
      setActiveTab("deploy");
    } catch (error) {
      console.error("Analysis Pipeline Error:", error);
      if (error.response && error.response.data) {
        setErrorMsg(error.response.data.message || "Pipeline execution failed.");
      } else {
        setErrorMsg("Unable to connect to backend server at http://localhost:8080.");
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
          Automated multi-cloud architecture recommendations, cost estimation & CI/CD pipeline automation
        </p>
      </header>

      {/* Main Navigation Tabs */}
      <nav className="main-nav">
        <button
          className={`nav-tab ${activeTab === "deploy" ? "active" : ""}`}
          onClick={() => setActiveTab("deploy")}
        >
          🚀 New Deployment
        </button>
        <button
          className={`nav-tab ${activeTab === "analytics" ? "active" : ""}`}
          onClick={() => setActiveTab("analytics")}
        >
          📊 Cloud Analytics & Cost Estimation
        </button>
        <button
          className={`nav-tab ${activeTab === "history" ? "active" : ""}`}
          onClick={() => setActiveTab("history")}
        >
          📜 Deployment History & Logs
        </button>
      </nav>

      {/* VIEW 1: NEW DEPLOYMENT FORM */}
      {activeTab === "deploy" && (
        <main>
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

            {/* Config Grid */}
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

            {/* Docker Registry Config */}
            <div className="form-grid-2">
              <div className="input-group">
                <label className="input-label">Docker Registry Support</label>
                <select
                  className="select-input"
                  value={dockerRegistry}
                  onChange={(e) => setDockerRegistry(e.target.value)}
                >
                  <option value="NONE">None (Local Only)</option>
                  <option value="DOCKER_HUB">Docker Hub</option>
                  <option value="AWS_ECR">Amazon ECR</option>
                </select>
              </div>

              {dockerRegistry !== "NONE" && (
                <div className="input-group">
                  <label className="input-label">Target Image Repository</label>
                  <input
                    type="text"
                    className="text-input"
                    placeholder="my-username/my-app"
                    value={dockerRepository}
                    onChange={(e) => setDockerRepository(e.target.value)}
                  />
                </div>
              )}
            </div>

            {/* Checkbox Options */}
            <div className="checkbox-group">
              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={highAvailability}
                  onChange={(e) => setHighAvailability(e.target.checked)}
                />
                <span>High Availability (Multi-AZ ALB & Auto Scaling)</span>
              </label>

              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={generateKubernetes}
                  onChange={(e) => setGenerateKubernetes(e.target.checked)}
                />
                <span>Generate Kubernetes Manifests (`k8s/`)</span>
              </label>

              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={generateHelm}
                  onChange={(e) => setGenerateHelm(e.target.checked)}
                />
                <span>Generate Helm Chart (`helm/`)</span>
              </label>

              <label className="checkbox-container">
                <input
                  type="checkbox"
                  checked={deployToAws}
                  onChange={(e) => setDeployToAws(e.target.checked)}
                />
                <span>Provision AWS Infrastructure & Deploy</span>
              </label>
            </div>

            {/* Submit Button */}
            <button className="submit-btn" onClick={handleAnalyze} disabled={loading}>
              {loading ? (
                <>
                  <div className="spinner"></div>
                  <span>Analyzing, Provisioning & Deploying...</span>
                </>
              ) : (
                <span>🚀 Execute Automated Deployment Pipeline</span>
              )}
            </button>

            {errorMsg && (
              <div style={{ marginTop: "20px", color: "#f43f5e", fontWeight: "600" }}>
                ⚠️ {errorMsg}
              </div>
            )}
          </div>

          {/* ANALYSIS RESULTS SECTION */}
          {result && (
            <div>
              {/* Endpoint Banner if Deployed */}
              {result.deploymentUrl && (
                <div className="endpoint-banner">
                  <div>
                    <span className="badge badge-success">LIVE APPLICATION ENDPOINT</span>
                    <div className="endpoint-title" style={{ marginTop: "8px" }}>
                      🔗 {result.deploymentUrl}
                    </div>
                  </div>
                  <a
                    href={result.deploymentUrl}
                    target="_blank"
                    rel="noreferrer"
                    className="endpoint-link"
                  >
                    Open Application ↗
                  </a>
                </div>
              )}

              {/* Cards Grid */}
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
                      <span className="data-value">{result.repository}</span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Language:</span>
                      <span className="data-value">{result.language}</span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Framework:</span>
                      <span className="data-value">{result.framework}</span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Build Tool:</span>
                      <span className="data-value">{result.buildTool}</span>
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
                      <span className="data-value">
                        {result.computeService} ({result.instanceType})
                      </span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Hardware Specs:</span>
                      <span className="data-value">
                        {result.cpu} vCPU / {result.memory || result.memoryGb} GB / {result.storage || result.storageGb} GB
                      </span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Load Balancer:</span>
                      <span
                        className={`badge ${result.loadBalancer ? "badge-success" : "badge-warning"}`}
                      >
                        {result.loadBalancer ? "Multi-AZ ALB" : "Disabled"}
                      </span>
                    </div>
                  </div>
                </div>

                {/* Cost Estimation Card */}
                {result.costEstimate && (
                  <div className="info-card">
                    <div className="card-header">
                      <span className="card-icon">💰</span>
                      <h3 className="card-title">AWS Cost Estimation</h3>
                    </div>
                    <div className="data-list">
                      <div className="data-row">
                        <span className="data-label">EC2 Compute:</span>
                        <span className="data-value">${result.costEstimate.ec2MonthlyCost} / mo</span>
                      </div>
                      <div className="data-row">
                        <span className="data-label">EBS Storage (gp3):</span>
                        <span className="data-value">${result.costEstimate.ebsStorageMonthlyCost} / mo</span>
                      </div>
                      <div className="data-row">
                        <span className="data-label">ALB Load Balancer:</span>
                        <span className="data-value">${result.costEstimate.albMonthlyCost} / mo</span>
                      </div>
                      <div className="cost-total">
                        ${result.costEstimate.totalMonthlyCost} <span style={{ fontSize: "0.9rem", color: "var(--text-muted)" }}>/ mo USD</span>
                      </div>
                    </div>
                  </div>
                )}

                {/* Pipeline Status Card */}
                <div className="info-card">
                  <div className="card-header">
                    <span className="card-icon">⚙️</span>
                    <h3 className="card-title">Pipeline Execution Status</h3>
                  </div>
                  <div className="data-list">
                    <div className="data-row">
                      <span className="data-label">Dockerfile / Jenkinsfile:</span>
                      <span className="badge badge-success">Generated</span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Terraform / K8s / Helm:</span>
                      <span className="badge badge-success">Generated</span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">Docker Build & Registry:</span>
                      <span
                        className={`badge ${result.pushedToRegistry ? "badge-success" : "badge-info"}`}
                      >
                        {result.pushedToRegistry ? "Pushed to Registry" : "Built Locally"}
                      </span>
                    </div>
                    <div className="data-row">
                      <span className="data-label">AWS Terraform Provision:</span>
                      <span
                        className={`badge ${result.awsDeployed ? "badge-success" : "badge-warning"}`}
                      >
                        {result.awsDeployed ? "Provisioned & Live" : "Generated Only"}
                      </span>
                    </div>
                  </div>
                </div>
              </div>

              {/* DOWNLOAD ARTIFACTS SECTION */}
              <section className="download-section">
                <h3 style={{ color: "#fff", fontSize: "1.3rem", fontWeight: "600", marginBottom: "8px" }}>
                  📥 Download Deployment Artifacts
                </h3>
                <p style={{ color: "var(--text-muted)", fontSize: "0.95rem" }}>
                  One-click downloads for project repository: <strong>{result.repository}</strong>
                </p>

                <div className="download-grid">
                  <a
                    href={getDownloadUrl("dockerfile", result.repository)}
                    download
                    className="download-btn"
                  >
                    🐳 Dockerfile
                  </a>
                  <a
                    href={getDownloadUrl("jenkinsfile", result.repository)}
                    download
                    className="download-btn"
                  >
                    🏗️ Jenkinsfile
                  </a>
                  <a
                    href={getDownloadUrl("terraform", result.repository)}
                    download
                    className="download-btn"
                  >
                    🌐 Terraform (ZIP)
                  </a>
                  <a
                    href={getDownloadUrl("kubernetes", result.repository)}
                    download
                    className="download-btn"
                  >
                    ☸️ Kubernetes (ZIP)
                  </a>
                  <a
                    href={getDownloadUrl("helm", result.repository)}
                    download
                    className="download-btn"
                  >
                    🛞 Helm Chart (ZIP)
                  </a>
                  <a
                    href={getDownloadUrl("report", result.repository)}
                    download
                    className="download-btn"
                  >
                    📄 Report (.txt)
                  </a>
                  <a
                    href={getDownloadUrl("pdf", result.repository)}
                    download
                    className="download-btn download-btn-pdf"
                  >
                    📕 Dissertation PDF
                  </a>
                </div>
              </section>
            </div>
          )}
        </main>
      )}

      {/* VIEW 2: ANALYTICS & COST ESTIMATION */}
      {activeTab === "analytics" && (
        <main>
          <div className="info-card" style={{ marginBottom: "30px" }}>
            <h2 style={{ fontSize: "1.6rem", marginBottom: "12px" }}>
              📊 Automated AWS Cloud Architecture & Cost Engine
            </h2>
            <p style={{ color: "var(--text-muted)", marginBottom: "20px" }}>
              Dynamic compute sizing and monthly expenditure calculation based on active user load and high availability parameters.
            </p>

            <div className="cards-grid">
              <div style={{ background: "rgba(15,23,42,0.6)", padding: "20px", borderRadius: "12px" }}>
                <h4 style={{ color: "#818cf8" }}>Small Workload (1 - 100 Users)</h4>
                <p style={{ marginTop: "8px", color: "var(--text-muted)" }}>
                  Single t3.micro EC2 (1 vCPU, 2GB RAM) + 20GB gp3 EBS
                </p>
                <div style={{ fontSize: "1.4rem", fontWeight: "700", color: "#34d399", marginTop: "12px" }}>
                  ~$9.19 / month USD
                </div>
              </div>

              <div style={{ background: "rgba(15,23,42,0.6)", padding: "20px", borderRadius: "12px" }}>
                <h4 style={{ color: "#c084fc" }}>Medium Workload (100 - 1000 Users)</h4>
                <p style={{ marginTop: "8px", color: "var(--text-muted)" }}>
                  Multi-AZ t3.medium EC2 (2 vCPU, 4GB RAM) + ALB Load Balancer
                </p>
                <div style={{ fontSize: "1.4rem", fontWeight: "700", color: "#34d399", marginTop: "12px" }}>
                  ~$81.36 / month USD
                </div>
              </div>

              <div style={{ background: "rgba(15,23,42,0.6)", padding: "20px", borderRadius: "12px" }}>
                <h4 style={{ color: "#f43f5e" }}>Enterprise Workload (1000+ Users)</h4>
                <p style={{ marginTop: "8px", color: "var(--text-muted)" }}>
                  Multi-AZ Auto-Scaling t3.large (4 vCPU, 8GB RAM) + ALB + ECR
                </p>
                <div style={{ fontSize: "1.4rem", fontWeight: "700", color: "#34d399", marginTop: "12px" }}>
                  ~$172.90+ / month USD
                </div>
              </div>
            </div>
          </div>
        </main>
      )}

      {/* VIEW 3: DEPLOYMENT MONITORING & LOGS */}
      {activeTab === "history" && (
        <main>
          <div className="info-card">
            <h2 style={{ fontSize: "1.6rem", marginBottom: "16px" }}>
              📜 Real-Time Deployment Monitoring & History
            </h2>

            {history && history.length > 0 ? (
              <div>
                <div className="data-list" style={{ marginBottom: "24px" }}>
                  {history.map((rec) => (
                    <div
                      key={rec.id}
                      className="data-row"
                      style={{
                        background: selectedHistoryRecord?.id === rec.id ? "rgba(99,102,241,0.2)" : "rgba(15,23,42,0.4)",
                        padding: "16px",
                        borderRadius: "12px",
                        cursor: "pointer",
                      }}
                      onClick={() => setSelectedHistoryRecord(rec)}
                    >
                      <div>
                        <strong>{rec.repository}</strong> ({rec.sourceType})
                        <div style={{ fontSize: "0.8rem", color: "var(--text-muted)" }}>
                          ID: {rec.id} | Env: {rec.environment} | {new Date(rec.timestamp).toLocaleString()}
                        </div>
                      </div>
                      <span className="badge badge-success">{rec.status}</span>
                    </div>
                  ))}
                </div>

                {selectedHistoryRecord && (
                  <div>
                    <h3 style={{ color: "#fff", marginBottom: "8px" }}>
                      Pipeline Logs: {selectedHistoryRecord.repository} ({selectedHistoryRecord.id})
                    </h3>
                    <div className="terminal-card">
                      {selectedHistoryRecord.logs && selectedHistoryRecord.logs.length > 0 ? (
                        selectedHistoryRecord.logs.map((logLine, idx) => (
                          <div key={idx}>&gt; {logLine}</div>
                        ))
                      ) : (
                        <div>No logs recorded.</div>
                      )}
                    </div>
                  </div>
                )}
              </div>
            ) : (
              <p style={{ color: "var(--text-muted)" }}>
                No deployment records found. Execute a deployment pipeline above to view history.
              </p>
            )}
          </div>
        </main>
      )}
    </div>
  );
}
