import React from 'react';
import axios from 'axios';

class Agents extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            runningAgents: [],
            agentClasses: [],
            agClass: '',
            agName: '',
            names: [
                "nekretnine.rs",
                "4zida.rs",
                "nadjidom.com"
            ]
        }
    }

    setAgentClass = e => {
        this.setState({ agClass: e.target.value });
    }

    setAgentName = e => {
        this.setState({ agName: e.target.value});
    }

    socketFunc = () => {
        var urlReg = this.props.wsURL;
        var socketReg = new WebSocket(urlReg);
        socketReg.onmessage = (msg) => {
            console.log(socketReg.readyState + " ---- (message) ---- ");
            console.log(JSON.parse(msg.data));
            this.setState({ runningAgents: JSON.parse(msg.data) });
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

    startAgentFunction = () => {
        axios.put(this.props.masterURL + "/client/agents/running/" + this.state.agClass + "/" + this.state.agName)
            .then(response => {
                console.log(response);
                this.setState({agClass: '', agName: ''})
                this.getRunningAgents();
            })
            .catch(error => {
                console.log(error);
            });
    }

    stopAgentFunction = (agent) => {
        var config = {
            headers: { 'Content-Type': 'application/json' },
            data: {
                host: agent.host,
                type: agent.type,
                name: agent.name
            }
        };
        axios.delete(this.props.masterURL + "/client/agents/running", config)
            .then(response => {
                console.log(response);
                this.getRunningAgents();
            })
            .catch(error => {
                console.log(error);
            });
    }

    componentDidMount() {
        axios.get(this.props.masterURL + "/client/agents/classes")
            .then(response => {
                console.log(response);
                this.setState({
                    agentClasses: response.data
                })
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
                    <div>
                        <h2>Agent Types</h2>
                        <hr />
                        <table>
                            {this.state.agentClasses.map(agentClass => {
                                return <tr>
                                    <td> {agentClass.module} </td>
                                </tr>
                            })}
                        </table>
                        <br />
                    </div>
                    <div className="row">
                        <div className="col">
                            <h2>Start Agent</h2>
                            <hr />
                            <div className="row">
                                <div className="col-3">
                                    <div className="dropdown">
                                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            Agent Type
                                        </button>
                                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            {this.state.agentClasses.map(agentClass => {
                                                return <button className="dropdown-item" value={agentClass.module} onClick={this.setAgentClass}>{agentClass.module}</button>
                                            })}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-3">
                                    <div className="dropdown">
                                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            Agent Name
                                        </button>
                                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                            {this.state.names.map(name => {
                                                return <button className="dropdown-item" value={name} onClick={this.setAgentName}>{name}</button>
                                            })}
                                        </div>
                                    </div>
                                </div>
                                <div className="col-7">
                                    {this.state.agClass === '' ? null : <label>Type: <input value={this.state.agClass} disabled className="sender" /></label>}
                                    {this.state.agName === '' ? null : <label>Name: <input value={this.state.agName} disabled className="sender" /></label>}
                                    {this.state.agClass === '' || this.state.agName === '' ? null : <button className="btn start-stop" onClick={this.startAgentFunction}>Start</button>}
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
                <div className="col-8">
                    <h2>Running Agents</h2>
                    <hr />
                    <div className="all-nodes">
                        <table>
                            <tr><td><strong>Agent Name</strong></td><td><strong>Host Address</strong></td><td><strong>Type</strong></td><td><strong></strong></td></tr>
                            {this.state.runningAgents.map(agent => {
                                return <tr>
                                    <td>{agent.name}</td>
                                    <td>{agent.host.address}</td>
                                    <td><em>{agent.type.type}</em></td>
                                    <td><button className="btn start-stop" onClick={() => this.stopAgentFunction(agent)}>Stop</button></td>
                                </tr>
                            })}
                        </table>
                    </div>
                </div>
            </div>
        );

    }
}

export default Agents;