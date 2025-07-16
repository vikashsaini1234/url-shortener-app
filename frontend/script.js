document.getElementById('anonForm').onsubmit = async function(e) {
    e.preventDefault();
    const data = new URLSearchParams();
    data.append("url", document.getElementById("url").value);

    const res = await fetch("http://localhost:8000/shorten", {
        method: "POST",
        body: data
    });
    const text = await res.text();
    document.getElementById("result").innerText = text;
}
