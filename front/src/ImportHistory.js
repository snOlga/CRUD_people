import './styles/App.css';

function ImportHistory({history}) {

    console.log(history)

    return (
        <div>
            {
                history.map((node) => (<div>
                    {node.fileName} - {node.isSuccessful == true ? 'imported' : 'failed'}
                    <br />
                </div>))
            }
        </div>
    )
}

export default ImportHistory;
