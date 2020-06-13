import React from 'react';
import axios from 'axios';

class Messages extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            performatives: [],
            runningAgents: []
        }
    }

    socketFunc = () => {
        var urlReg = this.props.wsURL;
        var socketReg = new WebSocket(urlReg);
        socketReg.onmessage = (msg) => {
            console.log(socketReg.readyState + " ---- (message) ---- ");
            console.log(JSON.parse(msg.data));
            this.setState({runningAgents: JSON.parse(msg.data)});
        }
        socketReg.onclose = () => socketReg = null;
    }

    getRunningAgents = () => {
        axios.get(this.props.masterURL + "/client/agents/running")
            .then(response => {
                console.log(response);
                this.setState({
                    runningAgents: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
    }

    componentDidMount() {
        axios.get(this.props.masterURL + "/client/messages")
            .then(response => {
                console.log(response);
                this.setState({ performatives: response.data })
            })
            .catch(error => {
                console.log(error);
            });

        this.getRunningAgents();
        this.socketFunc();
    }

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Performatives</h2>
                    <hr />
                    <div className="perfs">
                        {this.state.performatives.map(p => <p>{p}</p>)}
                    </div>
                </div>
                <div className="col-8">
                    <h2>Create Message</h2>
                    <hr />
                    <h4>Receiver</h4>
                    {this.state.runningAgents.map(agent => <p>{agent.name}</p>)}
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/messages]
                </div>
            </div>
        );

    }
}

export default Messages;