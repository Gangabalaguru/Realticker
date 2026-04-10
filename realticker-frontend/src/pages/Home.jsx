import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function Home() {
  const [stocks, setStocks] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
  axios.get(`${import.meta.env.VITE_BACKEND_URL}/top10`)
    .then(res => {

      setStocks(res.data); // temporary
      setLoading(false);
    })
    .catch(() => setLoading(false));
}, []);

  if (loading) return <p className="p-6 text-center">Loading...</p>;

  return (
    <div className="min-h-screen bg-gray-100 flex justify-center items-start py-10">
      
      {/* MAIN CONTAINER */}
      <div className="w-full max-w-5xl bg-white shadow-lg rounded-xl p-6">

        <h1 className="text-3xl font-bold mb-6 text-center">
           Top 10 Stocks
        </h1>

        <table className="w-full border rounded overflow-hidden">
          <thead>
            <tr className="bg-gray-200 text-left">
              <th className="p-3">Ticker</th>
              <th className="p-3">Company</th>
              <th className="p-3">Price</th>
              <th className="p-3">Change %</th>
              <th className="p-3">Volume</th>
            </tr>
          </thead>

          <tbody>
            {stocks.map((s, i) => (
              <tr
                key={i}
                className="cursor-pointer hover:bg-gray-100 transition"
                onClick={() => navigate(`/stock/${s.ticker}`)}
              >
                <td className="p-3">{s.ticker}</td>
                <td className="p-3">{s.company}</td>
                <td className="p-3">{s.price.toFixed(2)}</td>
                <td
                  className={`p-3 font-semibold ${
                    s.changePercent > 0 ? "text-green-600" : "text-red-600"
                  }`}
                >
                  {s.changePercent.toFixed(2)}%
                </td>
                <td className="p-3">{s.volume}</td>
              </tr>
            ))}
          </tbody>
        </table>

      </div>
    </div>
  );
}