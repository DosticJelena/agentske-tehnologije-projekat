import React from 'react';

class Results extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            resultFile: ''
        }
    }

    socketFunc = () => {
        var urlReg = this.props.wsURL + "/results";
        var socketReg = new WebSocket(urlReg);
        socketReg.onmessage = (msg) => {
            console.log(socketReg.readyState + " ---- (message) ---- ");
            console.log(msg.data);
            this.setState({ resultFile: msg.data });
        }
        socketReg.onclose = () => socketReg = null;
    }

    componentDidMount() {
        this.socketFunc();
    }

    render() {
        return (
            <div className="row">
                {this.state.resultFile}
            </div>
        );

    }
}

export default Results;