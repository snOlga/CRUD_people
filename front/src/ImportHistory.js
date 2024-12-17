import './styles/App.css';

function ImportHistory({ history }) {

    function getCookie(cname) {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for (let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    function download(filename) {
        fetch('http://localhost:17617/api/files/get_one', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: filename
        })
            .then((response) => response.json())
            .then(blob => {
                console.log(blob)

                const url = window.URL.createObjectURL(new Blob([JSON.stringify(blob)], { type: "application/json" }));
                const a = document.createElement('a');
                a.href = url;
                a.download = filename;
                document.body.appendChild(a);
                a.click();

                window.URL.revokeObjectURL(url);
                document.body.removeChild(a);
            })
    }

    return (
        <div>
            {
                history.map((node) => {
                    if (getCookie("CurrentUser") == node.owner.nickname) {
                        return (
                            <div>
                                {node.fileName} - {node.isSuccessful} - <button onClick={(e) => download(node.fileName)}>download</button>
                                <br />
                            </div>
                        )
                    }
                    else {
                        <div></div>
                    }
                })
            }
        </div>
    )
}

export default ImportHistory;
