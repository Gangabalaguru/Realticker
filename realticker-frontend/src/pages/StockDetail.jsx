import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { LineChart, Line, XAxis, YAxis, Tooltip } from "recharts";

export default function StockDetail() {
  const { ticker } = useParams();

  const [analysis, setAnalysis] = useState(null);
  const [history, setHistory] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);

    // ✅ Call both APIs once
    Promise.all([
      axios.post(`http://localhost:8081/api/stocks/${ticker}/analyze`),
      axios.get(`http://localhost:8081/api/stocks/${ticker}/history`)
    ])
      .then(([analysisRes, historyRes]) => {
        setAnalysis(analysisRes.data);

        const formatted = historyRes.data.map((value, index) => ({
          day: index,
          price: value
        }));

        setHistory(formatted);
      })
      .catch(err => {
        console.error("Error:", err);
      })
      .finally(() => {
        setLoading(false);
      });

  }, [ticker]);

  return (
    <div className="p-6">
      <h1 className="text-xl font-bold">Stock: {ticker}</h1>

      {loading ? (
        <p className="mt-4">⏳ Loading analysis...</p>
      ) : (
        <>
          {/* ✅ Analysis Card */}
          <div className="mt-4 border p-4 rounded shadow bg-white">
            <p><b>Trend:</b> {analysis?.trend}</p>
            <p><b>Risk:</b> {analysis?.risk}</p>
            <p><b>Suggestion:</b> {analysis?.suggestion}</p>

            <p className="text-red-500 text-sm mt-3">
              {analysis?.disclaimer}
            </p>
          </div>

          {/* ✅ Chart */}
          {history.length > 0 && (
            <div className="mt-6">
              <LineChart width={500} height={300} data={history}>
                <XAxis dataKey="day" />
                <YAxis />
                <Tooltip />
                <Line type="monotone" dataKey="price" />
              </LineChart>
            </div>
          )}
        </>
      )}
    </div>
  );
}